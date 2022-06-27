package com.example.instagram.dao;

import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import com.example.instagram.mapper.PostingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class PostingDao {
    private final PostingMapper postingMapper;

    public PostingDao(PostingMapper postingMapper) {
        this.postingMapper = postingMapper;
    }

    public PostingFindDto selectPostingById(Long postingId){
        log.info("dao:{}",postingId);
        return postingMapper.selectPostingById(postingId);
    }
    public List<PostingListDto> selectPostingList(){
        return postingMapper.selectPostingList();
    }
    public int insertPosting(String memberId,String title, String contents){
        return postingMapper.insertPosting(memberId,title,contents);
    }

}
