package com.example.board.service;

import com.example.board.mapper.BoardMapper;
import com.example.board.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardVO> getBoardList(){
        return boardMapper.getBoardList();
    }

    public List<BoardVO> getBoardListByCategory(String category) {
        return boardMapper.getBoardListByCategory(category);
    }

    public BoardVO getBoardDetail(int boardSeq) {
        boardMapper.increaseViewCount(boardSeq);
        return boardMapper.getBoardDetail(boardSeq);
    }

    public int insertBoard(BoardVO boardVO) {
        boardMapper.insertBoard(boardVO);
        return boardVO.getBoardSeq();
    }

    public void updateBoard(BoardVO boardVO) {
        boardMapper.updateBoard(boardVO);
    }

    public void deleteBoard(int boardSeq) {
        boardMapper.deleteBoard(boardSeq);
    }
}
