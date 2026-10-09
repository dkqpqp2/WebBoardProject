package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.vo.CommentVO;
import com.example.board.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith({MockitoExtension.class})
public class BoardViewControllerTest {

    @Mock
    BoardService boardService;

    @Mock
    CommentService commentService;

    @InjectMocks
    BoardViewController boardViewController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(boardViewController).build();
    }

    @Test
    @DisplayName("남의 댓글은 삭제 안 됨")
    void cannotDeleteOthersComment() throws Exception {
        UserVO user = new UserVO();
        user.setUserSeq(4);
        CommentVO comment = new CommentVO();
        comment.setCommentSeq(3);
        comment.setUserSeq(5);
        when(commentService.getCommentById(3)).thenReturn(comment);

        mockMvc.perform(post("/board/1/comment/3/delete").sessionAttr("loginUser", user))
                .andExpect(redirectedUrl("/board/detail/1"));

        verify(commentService, never()).deleteComment(3);
    }

    @Test
    @DisplayName("내 댓글은 삭제됨")
    void deleteOwnComment() throws Exception {
        UserVO user = new UserVO();
        user.setUserSeq(4);
        CommentVO comment = new CommentVO();
        comment.setCommentSeq(3);
        comment.setUserSeq(4);
        when(commentService.getCommentById(3)).thenReturn(comment);

        mockMvc.perform(post("/board/1/comment/3/delete").sessionAttr("loginUser", user))
                .andExpect(redirectedUrl("/board/detail/1"));

        verify(commentService).deleteComment(3);
    }
}
