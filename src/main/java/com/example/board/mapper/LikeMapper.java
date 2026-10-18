package com.example.board.mapper;

import com.example.board.vo.LikeVO;

public interface LikeMapper {
    void insertLike(LikeVO likeVO);
    void deleteLike(LikeVO likeVO);
    int countLikes(int boardSeq);
    int existsLike(LikeVO likeVO);
    void deleteLikesByBoardSeq(int boardSeq);
}
