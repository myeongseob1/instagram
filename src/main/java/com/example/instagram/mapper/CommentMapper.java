package com.example.instagram.mapper;

import com.example.instagram.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insertComment(String memberId,Long postingId, String contents);
    List<CommentDto> selectCommentList(Long postingId);
}
