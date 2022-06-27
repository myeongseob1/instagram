package com.example.instagram.mapper;

import com.example.instagram.domain.Posting;
import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostingMapper {
    //member_id,title,contents,register_time
    int insertPosting(String memberId,String title, String contents);
    PostingFindDto selectPostingById(Long postingId);
    List<PostingListDto> selectPostingList();
}