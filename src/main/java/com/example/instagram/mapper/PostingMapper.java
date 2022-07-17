package com.example.instagram.mapper;

import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostingMapper {
    int insertPosting(Long postingId,String memberId,String title, String contents);
    PostingFindDto selectPostingById(Long postingId);
    List<PostingListDto> selectPostingList();
    int deletePosting(Long postingId);
    int updatePosting(Long postingId,String title, String contents);
    int insertFile(Long postingId, String imageUrl);
    int insertPostingLike(Long postingId, String memberId);
}
