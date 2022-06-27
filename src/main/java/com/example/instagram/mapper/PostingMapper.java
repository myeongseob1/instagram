package com.example.instagram.mapper;

import com.example.instagram.domain.Posting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostingMapper {
    //member_id,title,contents,register_time
    int insertPosting(String memberId,String title, String contents);
    Posting getPosting(String postingId);
}
