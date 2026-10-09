package com.example.board.service;

import com.example.board.mapper.BoardMapper;
import com.example.board.mapper.CommentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class BoardServiceTest {

    @Mock
    BoardMapper boardMapper;

    @Mock
    CommentMapper commentMapper;

    @InjectMocks
    BoardService boardService;

    @Test
    @DisplayName("글이 21개면 3페이지")
    void totalPageRoundUp(){
        when(boardMapper.getBoardCount()).thenReturn(21);

        int result = boardService.getTotalPage(null);

        assertEquals(3, result);
    }

    @Test
    @DisplayName("글이 20개면 2페이지")
    void totalPageExact(){
        when(boardMapper.getBoardCount()).thenReturn(20);

        int result = boardService.getTotalPage(null);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("카테고리를 고르면 그 카테고리 글 개수로 계산")
    void totalPageByCategory(){
        when(boardMapper.getBoardCountByCategory("IT")).thenReturn(11);

        int result = boardService.getTotalPage("IT");

        assertEquals(2, result);
    }

    @Test
    @DisplayName("2페이지는 10개 건너뛰고 조회")
    void secondPageOffset(){
        boardService.getBoardList(2);

        verify(boardMapper).getBoardListPaged(10, 10);
    }

    @Test
    @DisplayName("글 삭제하면 댓글도 같이 삭제")
    void deleteBoardWithComments(){
        boardService.deleteBoard(7);

        verify(commentMapper).deleteCommentsByBoardSeq(7);
        verify(boardMapper).deleteBoard(7);
    }
}
