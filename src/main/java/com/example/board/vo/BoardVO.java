package com.example.board.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardVO {
    private int boardSeq;
    private int userSeq;
    private String userName;
    private String category;
    private String boardTitle;
    private String boardContent;
    private int viewCount;
    private LocalDateTime boardWritedate;
    private LocalDateTime boardUpdate;
}
