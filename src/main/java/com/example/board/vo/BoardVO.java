package com.example.board.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardVO {
    private int boardSeq;
    private int userSeq;
    private String userName;
    @NotBlank
    private String category;
    @NotBlank(message = "제목을 입력해주세요")
    @Size(max = 200, message = "제목은 200자 이하로 입력해주세요")
    private String boardTitle;
    @NotBlank
    private String boardContent;
    private int viewCount;
    private LocalDateTime boardWritedate;
    private LocalDateTime boardUpdate;
}
