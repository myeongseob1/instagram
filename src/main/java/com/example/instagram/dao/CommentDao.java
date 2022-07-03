package com.example.instagram.dao;

import com.example.instagram.dto.CommentDto;
import com.example.instagram.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class CommentDao {
    private final CommentMapper commentMapper;

    public CommentDao(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public List<CommentDto> selectCommentList(Long postingId){
        return commentMapper.selectCommentList(postingId);
    }

    public int insertComment(String memberId,Long postingId, String contents){
        return commentMapper.insertComment(memberId,postingId,contents);
    }

}
