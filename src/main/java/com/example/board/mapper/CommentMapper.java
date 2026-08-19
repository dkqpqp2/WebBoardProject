package com.example.board.mapper;

import com.example.board.vo.CommentVO;

import java.util.List;

public interface CommentMapper {

    List<CommentVO> getCommentList(int boardSeq);
    CommentVO getCommentById(int commentSeq);

    void insertComment(CommentVO commentVO);
    void updateComment(CommentVO commentVO);
    void deleteComment(int commentSeq);
}
