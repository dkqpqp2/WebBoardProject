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
    private static final int PAGE_SIZE = 10;

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

    public List<BoardVO> getBoardList(int page) {
        int offset = (page - 1) * PAGE_SIZE;
        return boardMapper.getBoardListPaged(offset, PAGE_SIZE);
    }

    public List<BoardVO> getBoardListByCategoryPaged(String category, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        return boardMapper.getBoardListByCategoryPaged(category, offset, PAGE_SIZE);
    }

    public int getTotalPage(String category) {
        int count = (category == null || category.isBlank())
                ? boardMapper.getBoardCount()
                : boardMapper.getBoardCountByCategory(category);
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

}
