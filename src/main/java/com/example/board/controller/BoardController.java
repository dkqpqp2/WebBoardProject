package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;
import com.example.board.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public List<BoardVO> getBoardList(){
        return boardService.getBoardList();
    }

    @GetMapping("/{boardSeq}")
    public BoardVO getBoardDetail(@PathVariable int boardSeq) {
        BoardVO board = boardService.getBoardDetail(boardSeq);
        if (board == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return board;
    }

    @PostMapping("/write")
    public int insertBoard(@RequestBody BoardVO boardVO, HttpSession session) {
        UserVO loginUser = requireLogin(session);
        boardVO.setUserSeq(loginUser.getUserSeq());
        return boardService.insertBoard(boardVO);
    }

    @PutMapping("/{boardSeq}")
    public void updateBoard(@PathVariable int boardSeq, @RequestBody BoardVO boardVO, HttpSession session) {
        requireOwner(session, boardSeq);
        boardVO.setBoardSeq(boardSeq);
        boardService.updateBoard(boardVO);
    }

    @DeleteMapping("/{boardSeq}")
    public void deleteBoard(@PathVariable int boardSeq, HttpSession session) {
        requireOwner(session, boardSeq);
        boardService.deleteBoard(boardSeq);
    }

    private UserVO requireLogin(HttpSession session) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return loginUser;
    }

    private void requireOwner(HttpSession session, int boardSeq) {
        UserVO loginUser = requireLogin(session);
        BoardVO board = boardService.getBoard(boardSeq);
        if (board == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (board.getUserSeq() != loginUser.getUserSeq()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
