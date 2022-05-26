package com.example.instagram.service;

import com.example.instagram.dao.UserDao;
import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Member login(String userId, String userPw) throws NoSuchAlgorithmException {
        String encPw = encrypt(userPw);
        Member member = userDao.getUser(userId, encPw);
        if(member == null){
            log.warn("user not exist");
            return null;
        }
        return member;
    }


    //SHA256 - 단방향 -> PW 같이 대조 이외의 용도로 사용할 이유가 없는 것들은 단방향 암호화
    //RSA - 양방향 -> EMAIL 같이 대조 이외의 용도로 사용할 이유가 있으면 양방향 암호화

    public String register(UserDto userDto) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException {
        //pw SHA256
        String userPw = encrypt(userDto.getUserPw());


        //email, phone RSA
        KeyPair keyPair = genRSAKeyPair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());


        int keyResult = userDao.insertUserSecure(userDto.getUserId(), keyPair.getPublic().toString(), keyPair.getPrivate().toString());
        if(keyResult <= 0){
            log.error("DB key insert Error");
            return null;
        }

        String email = userDto.getEmail();

        String encEmail =  encryptRSA(email, getPublicKeyFromBase64Encrypted(publicKey));

        if (email.equals(decryptRSA(encEmail, getPrivateKeyFromBase64Encrypted(privateKey)))) {
            log.info("success");
        }
        int result = userDao.insertUser(userDto.getName(),encEmail,userDto.getUserId(),userPw,userDto.getPhone(),userDto.getNickname());

        if(result <= 0){
            log.error("DB user insert error");
            return null;
        }
        return "success";
    }

    /**
     * SHA-256 - PW 같이 대조 이외의 용도로 사용할 이유가 없는 것들은 단방향 암호화를 위해 사용
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /**
     * 1024비트 RSA 키쌍을 생성
     */
    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024, new SecureRandom());
        return gen.genKeyPair();
    }

    /**
     * Public Key로 RSA 암호화를 수행
     */
    public static String encryptRSA(String plainText, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(bytePlain);
    }

    /**
     * Private Key로 RSA 복호화를 수행
     */
    public static String decryptRSA(String encrypted, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        return new String(bytePlain, "utf-8");
    }

    public static PublicKey getPublicKeyFromBase64Encrypted(String base64PublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PubKey = Base64.getDecoder().decode(base64PublicKey);

        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decodedBase64PubKey));
    }

    public static PrivateKey getPrivateKeyFromBase64Encrypted(String base64PrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PrivateKey = Base64.getDecoder().decode(base64PrivateKey);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedBase64PrivateKey));
    }
}
