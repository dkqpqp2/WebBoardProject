package com.example.board.service;

import com.example.board.mapper.CommentMapper;
import com.example.board.vo.CommentVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
public class CommentServiceTest {

    @Mock
    CommentMapper commentMapper;

    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("댓글 번호로 조회하면 Mapper가 준 댓글을 그대로 돌려준다")
    void getCommentByIdReturnMapperResult(){
        CommentVO comment = new CommentVO();
        comment.setCommentSeq(3);
        when(commentMapper.getCommentById(3)).thenReturn(comment);

        CommentVO result = commentService.getCommentById(3);

        assertSame(comment, result);
    }

    @Test
    @DisplayName("댓글 삭제 시 Mapper 삭제가 호출")
    void deleteCallMapper(){
        commentService.deleteComment(5);

        verify(commentMapper).deleteComment(5);
    }

    @Test
    @DisplayName("댓글 쓰면 Mapper 댓글쓰기 호출")
    void commentWriteMapper(){
        CommentVO comment = new CommentVO();
        comment.setCommentSeq(13);
        int result = commentService.insertComment(comment);

        verify(commentMapper).insertComment(comment);

        assertEquals(13, result);

    }
}
