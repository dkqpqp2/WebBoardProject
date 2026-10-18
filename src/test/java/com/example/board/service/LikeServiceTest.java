package com.example.board.service;

import com.example.board.mapper.LikeMapper;
import com.example.board.vo.LikeVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class LikeServiceTest {

    @Mock
    LikeMapper likeMapper;

    @InjectMocks
    LikeService likeService;

    @Test
    @DisplayName("좋아요를 안 눌렀으면 추가하고 true 반환")
    void toggleLikeAdd() {
        when(likeMapper.existsLike(any(LikeVO.class))).thenReturn(0);
        boolean result = likeService.toggleLike(5,4);

        verify(likeMapper).insertLike(any(LikeVO.class));
        assertTrue(result);
    }

    @Test
    @DisplayName("이미 좋아요를 누른 상태에서 다시 누르면 취소 false 반환")
    void toggleLikeRemove() {

        when(likeMapper.existsLike(any(LikeVO.class))).thenReturn(1);

        boolean result = likeService.toggleLike(5,4);

        verify(likeMapper).deleteLike(any(LikeVO.class));
        assertFalse(result);
    }
}
