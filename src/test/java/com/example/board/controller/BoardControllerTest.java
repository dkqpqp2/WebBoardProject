package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
public class BoardControllerTest {

    @Mock
    BoardService boardService;

    @InjectMocks
    BoardController boardController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    @DisplayName("로그인 안 하고 글쓰기하면 401")
    void writeWithoutLogin() throws Exception {
        mockMvc.perform(post("/api/board/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"FREE\",\"boardTitle\":\"제목\",\"boardContent\":\"내용\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("남의 글 수정하면 403")
    void updateOthersBoard() throws Exception {
        UserVO user = new UserVO();
        user.setUserSeq(4);
        BoardVO board = new BoardVO();
        board.setBoardSeq(1);
        board.setUserSeq(5);
        when(boardService.getBoard(1)).thenReturn(board);

        mockMvc.perform(put("/api/board/1")
                        .sessionAttr("loginUser", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"FREE\",\"boardTitle\":\"제목\",\"boardContent\":\"내용\"}"))
                .andExpect(status().isForbidden());

        verify(boardService, never()).updateBoard(any());
    }

    @Test
    @DisplayName("내 글 수정하면 성공")
    void updateOwnBoard() throws Exception {
        UserVO user = new UserVO();
        user.setUserSeq(4);
        BoardVO board = new BoardVO();
        board.setBoardSeq(1);
        board.setUserSeq(4);
        when(boardService.getBoard(1)).thenReturn(board);

        mockMvc.perform(put("/api/board/1")
                        .sessionAttr("loginUser", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"FREE\",\"boardTitle\":\"제목\",\"boardContent\":\"내용\"}"))
                .andExpect(status().isOk());

        verify(boardService).updateBoard(any());
    }

    @Test
    @DisplayName("없는 글 삭제하면 404")
    void deleteNoBoard() throws Exception {
        UserVO user = new UserVO();
        user.setUserSeq(4);
        when(boardService.getBoard(99)).thenReturn(null);

        mockMvc.perform(delete("/api/board/99").sessionAttr("loginUser", user))
                .andExpect(status().isNotFound());
    }
}
