package com.example.instagram.dao;

import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import com.example.instagram.mapper.PostingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
    public int insertPosting(String memberId,String title, String contents){
        return postingMapper.insertPosting(memberId,title,contents);
    }
    public int insertFile(Long postingId,String imageUrl){
        return postingMapper.insertFile(postingId,imageUrl);
    }

    public int deletePosting(Long postingId) {
        return postingMapper.deletePosting(postingId);
    }
    public int updatePosting(Long postingId,String title, String contents){
        return postingMapper.updatePosting(postingId,title, contents);
    }
}
