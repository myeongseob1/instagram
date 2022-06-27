package com.example.instagram.dao;

import com.example.instagram.domain.Posting;
import com.example.instagram.mapper.PostingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PostingDao {
    private final PostingMapper postingMapper;

    public PostingDao(PostingMapper postingMapper) {
        this.postingMapper = postingMapper;
    }

    public Posting getPosting(String postingId){
        return postingMapper.getPosting(postingId);
    }

    public int insertPosting(String memberId,String title, String contents){
        return postingMapper.insertPosting(memberId,title,contents);
    }

}
