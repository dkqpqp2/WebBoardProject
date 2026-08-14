package com.example.board.controller;

import com.example.board.service.CommentService;
import com.example.board.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/board/{boardSeq}/comment")
    public List<CommentVO> getCommentList(@PathVariable int boardSeq) {
        return commentService.getCommentList(boardSeq);
    }

    @PostMapping("/api/board/{boardSeq}/comment")
    public int insertComment(@PathVariable int boardSeq, @RequestBody CommentVO commentVO) {
        commentVO.setBoardSeq(boardSeq);
        return commentService.insertComment(commentVO);
    }

    @PutMapping("/api/comment/{commentSeq}")
    public void updateComment(@PathVariable int commentSeq, @RequestBody CommentVO commentVO) {
        commentVO.setCommentSeq(commentSeq);
        commentService.updateComment(commentVO);
    }

    @DeleteMapping("/api/comment/{commentSeq}")
    public void deleteComment(@PathVariable int commentSeq) {
        commentService.deleteComment(commentSeq);
    }
}
