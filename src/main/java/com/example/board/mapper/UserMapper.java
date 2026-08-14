package com.example.board.mapper;

import com.example.board.vo.UserVO;

public interface UserMapper {

    UserVO findByUserId(String userId);

    void insertUser(UserVO userVO);
}
