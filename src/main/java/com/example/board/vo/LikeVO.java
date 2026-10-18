package com.example.board.vo;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LikeVO {
    private int likeSeq;
    private int userSeq;
    private int boardSeq;
    private LocalDateTime likeDate;
}
