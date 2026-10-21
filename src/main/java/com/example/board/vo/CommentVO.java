package com.example.board.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentVO {
    private int commentSeq;
    private int userSeq;
    private String userName;
    private int boardSeq;
    @NotBlank
    @Size(max = 500, message = "댓글 500자 이하로 입력해주세요")
    private String commentContent;
    private LocalDateTime commentWritedate;
    private LocalDateTime commentUpdate;
}
