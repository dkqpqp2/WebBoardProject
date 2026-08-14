package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return boardService.getBoardDetail(boardSeq);
    }

    @PostMapping("/write")
    public int insertBoard(@RequestBody BoardVO boardVO) {
        return boardService.insertBoard(boardVO);
    }

    @PutMapping("/{boardSeq}")
    public void updateBoard(@PathVariable int boardSeq, @RequestBody BoardVO boardVO) {
        boardVO.setBoardSeq(boardSeq);
        boardService.updateBoard(boardVO);
    }

    @DeleteMapping("/{boardSeq}")
    public void deleteBoard(@PathVariable int boardSeq) {
        boardService.deleteBoard(boardSeq);
    }
}
