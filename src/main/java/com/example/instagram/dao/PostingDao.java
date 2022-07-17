package com.example.instagram.dao;

import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import com.example.instagram.mapper.PostingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PostingDao {
    private final PostingMapper postingMapper;


    public PostingFindDto selectPostingById(Long postingId){
        return postingMapper.selectPostingById(postingId);
    }
    public List<PostingListDto> selectPostingList(){
        return postingMapper.selectPostingList();
    }
    @Transactional
    public boolean  insertPosting(String memberId,String title, String contents,long postingId,String imageUrl){
        if(0>= postingMapper.insertPosting(postingId,memberId,title,contents)){
            return false;
        }
        if(imageUrl!=null){
            if(0>=postingMapper.insertFile(postingId,imageUrl)){
                return false;
            }
        }
        return true;
    }

    public int deletePosting(Long postingId) {
        return postingMapper.deletePosting(postingId);
    }
    public int updatePosting(Long postingId,String title, String contents){
        return postingMapper.updatePosting(postingId,title, contents);
    }

    public int insertPostingLike(Long postingId,String memberId){
        return postingMapper.insertPostingLike(postingId,memberId);
    }
}
