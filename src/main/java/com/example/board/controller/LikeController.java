package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.service.LikeService;
import com.example.board.vo.LikeResponse;
import com.example.board.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/board/{boardSeq}/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final BoardService boardService;

    @PostMapping
    public LikeResponse toggleLike(@PathVariable int boardSeq, HttpSession session) {
        UserVO loginUser = (UserVO)session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (boardService.getBoard(boardSeq) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        boolean liked = likeService.toggleLike(boardSeq, loginUser.getUserSeq());
        int likeCount = likeService.getLikeCount(boardSeq);
        return new LikeResponse(liked, likeCount);
    }

    @GetMapping
    public LikeResponse getLike (@PathVariable int boardSeq, HttpSession session) {
        if (boardService.getBoard(boardSeq) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        boolean liked = false;
        if (loginUser != null) {
            liked = likeService.isLiked(boardSeq, loginUser.getUserSeq());
        }

        int likeCount = likeService.getLikeCount(boardSeq);
        return new LikeResponse(liked, likeCount);
    }
}
