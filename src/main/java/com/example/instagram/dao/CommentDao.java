package com.example.instagram.dao;

import com.example.instagram.dto.CommentDto;
import com.example.instagram.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CommentDao {
    private final CommentMapper commentMapper;


    public List<CommentDto> selectCommentList(Long postingId){
        return commentMapper.selectCommentList(postingId);
    }
    public int insertComment(String memberId,Long postingId, String contents){
        return commentMapper.insertComment(memberId,postingId,contents);
    }
    public int deleteComment(Long commentId) {
        return commentMapper.deleteComment(commentId);
    }
    public int updateComment(Long commentId, String contents){
        return commentMapper.updateComment(commentId, contents);
    }

}
