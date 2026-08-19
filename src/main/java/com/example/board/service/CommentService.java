package com.example.board.service;

import com.example.board.mapper.CommentMapper;
import com.example.board.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public List<CommentVO> getCommentList(int boardSeq) {
        return commentMapper.getCommentList(boardSeq);
    }

    public CommentVO getCommentById(int commentSeq) {
        return commentMapper.getCommentById(commentSeq);
    }

    public int insertComment(CommentVO commentVO) {
        commentMapper.insertComment(commentVO);
        return commentVO.getCommentSeq();
    }

    public void updateComment(CommentVO commentVO) {
        commentMapper.updateComment(commentVO);
    }

    public void deleteComment(int commentSeq) {
        commentMapper.deleteComment(commentSeq);
    }
}
