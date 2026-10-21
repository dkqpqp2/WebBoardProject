package com.example.board.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
public class CommentControllerTest {

    @Mock
    CommentService commentService;

    @InjectMocks
    CommentController commentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @DisplayName("로그인 안 하고 댓글 삭제하면 401")
    void deleteWithoutLogin() throws Exception {
        mockMvc.perform(delete("/api/comment/3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("댓글 비어있으면 400")
    void writeWithEmptyComment() throws Exception {
         mockMvc.perform(post("/api/board/5/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commentContent\":\"\"}"))
            .andExpect(status().isBadRequest());

         verify(commentService, never()).insertComment(any());
    }

    @Test
    @DisplayName("남의 댓글 삭제하면 403")
    void deleteOthersComment() throws Exception {
        UserVO user = new UserVO();
        user.setUserSeq(4);
        CommentVO comment = new CommentVO();
        comment.setCommentSeq(3);
        comment.setUserSeq(5);
        when(commentService.getCommentById(3)).thenReturn(comment);

        mockMvc.perform(delete("/api/comment/3").sessionAttr("loginUser", user))
                .andExpect(status().isForbidden());

        verify(commentService, never()).deleteComment(3);
    }
}
