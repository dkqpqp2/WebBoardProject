package com.example.board.service;

import com.example.board.mapper.LikeMapper;
import com.example.board.vo.LikeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public boolean toggleLike (int boardSeq, int userSeq) {

        LikeVO like = new LikeVO();
        like.setBoardSeq(boardSeq);
        like.setUserSeq(userSeq);

        if(likeMapper.existsLike(like) > 0) {
            likeMapper.deleteLike(like);

            return false;
        }

        likeMapper.insertLike(like);
        return true;
    }

    public int getLikeCount(int boardSeq) {
        return likeMapper.countLikes(boardSeq);
    }

    public boolean isLiked(int boardSeq, int userSeq) {
        LikeVO like = new LikeVO();
        like.setBoardSeq(boardSeq);
        like.setUserSeq(userSeq);

        return likeMapper.existsLike(like) > 0;
    }
}
