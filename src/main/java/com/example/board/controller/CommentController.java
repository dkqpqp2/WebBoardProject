package com.example.board.controller;

import com.example.board.service.CommentService;
import com.example.board.vo.CommentVO;
import com.example.board.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public int insertComment(@PathVariable int boardSeq, @RequestBody CommentVO commentVO, HttpSession session) {
        UserVO loginUser = requireLogin(session);
        commentVO.setBoardSeq(boardSeq);
        commentVO.setUserSeq(loginUser.getUserSeq());
        return commentService.insertComment(commentVO);
    }

    @PutMapping("/api/comment/{commentSeq}")
    public void updateComment(@PathVariable int commentSeq, @RequestBody CommentVO commentVO, HttpSession session) {
        requireOwner(session, commentSeq);
        commentVO.setCommentSeq(commentSeq);
        commentService.updateComment(commentVO);
    }

    @DeleteMapping("/api/comment/{commentSeq}")
    public void deleteComment(@PathVariable int commentSeq, HttpSession session) {
        requireOwner(session, commentSeq);
        commentService.deleteComment(commentSeq);
    }

    private UserVO requireLogin(HttpSession session) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return loginUser;
    }

    private void requireOwner(HttpSession session, int commentSeq) {
        UserVO loginUser = requireLogin(session);
        CommentVO comment = commentService.getCommentById(commentSeq);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (comment.getUserSeq() != loginUser.getUserSeq()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
