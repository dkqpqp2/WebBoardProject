package com.example.board.mapper;

import com.example.board.vo.BoardVO;

import java.util.List;

public interface BoardMapper {

    List<BoardVO> getBoardList();
    BoardVO getBoardDetail(int boardSeq);

    void insertBoard(BoardVO boardVO);
    void updateBoard(BoardVO boardVO);
    void deleteBoard(int boardSeq);
}
