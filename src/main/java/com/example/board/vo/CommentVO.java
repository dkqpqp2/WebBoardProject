package com.example.board.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentVO {
    private int commentSeq;
    private int userSeq;
    private int boardSeq;
    private String commentContent;
    private LocalDateTime commentWritedate;
    private LocalDateTime commentUpdate;
}
