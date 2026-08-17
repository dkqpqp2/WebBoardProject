package com.example.board.mapper;

import com.example.board.vo.BoardVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {

    List<BoardVO> getBoardList();
    List<BoardVO> getBoardListByCategory(String category);
    BoardVO getBoardDetail(int boardSeq);

    void insertBoard(BoardVO boardVO);
    void updateBoard(BoardVO boardVO);
    void deleteBoard(int boardSeq);
    void increaseViewCount(int boardSeq);

    List<BoardVO> getBoardListPaged(@Param("offset") int offset, @Param("size") int size);
    List<BoardVO> getBoardListByCategoryPaged(@Param("category") String category, @Param("offset") int offset, @Param("size") int size);
    int getBoardCount();
    int getBoardCountByCategory(String category);

}
