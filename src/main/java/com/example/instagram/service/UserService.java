package com.example.instagram.service;

import com.example.instagram.dao.UserDao;
import com.example.instagram.domain.Member;
import com.example.instagram.domain.MemberSecure;
import com.example.instagram.dto.UserDto;
import com.example.instagram.dto.VerifyDto;
import com.example.instagram.exception.CommonErrorException;
import com.example.instagram.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final JwtTokenService jwtTokenService;
    private final RedisService redisService;


    public Member login(UserDto userDto) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException {
        Member member = userDao.getUser(userDto.getUserId());
        if(member == null || StringUtils.isBlank(member.getUserId())||!member.getUserPw().equals(encrypt(userDto.getUserPw()))){
            throw new CommonErrorException(ErrorCode.USER_LOGIN_ERROR);
        }

        MemberSecure memberSecure = userDao.getUserSecure(member.getMemberId());
        String privateKey = memberSecure.getPrivateKey();
        int loginTimeResult = userDao.updateLoginTime(member.getMemberId());
        if(loginTimeResult <= 0){
            log.warn("Login Time error");
        }
        if(!StringUtils.isBlank(member.getEmail())){
            member.setEmail(decryptRSA(member.getEmail(), getPrivateKeyFromBase64Encrypted(privateKey)));
        }
        if(!StringUtils.isBlank(member.getPhone())){
            member.setPhone(decryptRSA(member.getPhone(), getPrivateKeyFromBase64Encrypted(privateKey)));
        }
        member.setJwtToken(jwtTokenService.createToken(member.getUserId(),member.getName(),member.getEmail()));
        redisService.setValues(member.getMemberId(),member.getJwtToken());
        return member;
    }

    public String idValidChk(String userId){
        Member member = userDao.getUser(userId);
        if(member != null){
            throw new CommonErrorException(ErrorCode.USER_ID_DUPLICATE_ERROR);
        }
        return "success";
    }

    //SHA256 - ????????? -> PW ?????? ?????? ????????? ????????? ????????? ????????? ?????? ????????? ????????? ?????????
    //RSA - ????????? -> EMAIL ?????? ?????? ????????? ????????? ????????? ????????? ????????? ????????? ?????????
    public String register(UserDto userDto) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException {
        String memberId = UUID.randomUUID().toString();

        //pw SHA256
        String userPw = encrypt(userDto.getUserPw());

        //email, phone RSA ???????????? ????????? ????????????
        KeyPair keyPair = genRSAKeyPair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        PublicKey pk = keyPair.getPublic();
        //???????????? ???????????? ????????? ??????

        if(!StringUtils.isBlank(userDto.getEmail())){
            userDto.setEmail(encryptRSA(userDto.getEmail(), pk));
            if (userDto.getEmail().equals(decryptRSA(userDto.getEmail(), getPrivateKeyFromBase64Encrypted(privateKey)))) {
                log.info("success");
            }

        }
        if(!StringUtils.isBlank(userDto.getPhone())){
            userDto.setPhone(encryptRSA(userDto.getPhone(), pk));
        }


        int keyResult = userDao.insertUserSecure(memberId,privateKey,publicKey);
        if(keyResult <= 0) {
            throw new CommonErrorException(ErrorCode.USER_INSERT_ERROR);
        }

        int result = userDao.insertUser(memberId, userDto.getName(),userDto.getEmail(),userDto.getUserId(),userPw,userDto.getPhone(),userDto.getNickname());

        if(result <= 0){
            throw new CommonErrorException(ErrorCode.USER_INSERT_ERROR);
        }
        return memberId;
    }

    public void verify(VerifyDto verifyDto){
        String verifyToken = redisService.getValues(verifyDto.getMemberId());
        if(!verifyDto.getJwtToken().equals(verifyToken)){
            throw new CommonErrorException(ErrorCode.USER_TOKEN_AUTHORIZATION_ERROR);
        }
    }

    /**
     * SHA-256 - PW ?????? ?????? ????????? ????????? ????????? ????????? ?????? ????????? ????????? ???????????? ?????? ??????
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
     * 1024?????? RSA ????????? ??????
     */
    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024, new SecureRandom());
        return gen.genKeyPair();
    }

    /**
     * Public Key??? RSA ???????????? ??????
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
     * Private Key??? RSA ???????????? ??????
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
