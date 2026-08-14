package com.example.board.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserVO {
    private int userSeq;
    private String userId;
    private String userPw;
    private String userName;
    private String role;
    private LocalDateTime joinDate;
}
