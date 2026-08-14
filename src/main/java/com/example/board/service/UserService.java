package com.example.board.service;

import com.example.board.mapper.UserMapper;
import com.example.board.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean isUserIdDuplicate(String userId) {
        return userMapper.findByUserId(userId) != null;
    }

    public void signup(UserVO userVO) {
        userVO.setUserPw(passwordEncoder.encode(userVO.getUserPw()));
        userMapper.insertUser(userVO);
    }

    public UserVO login(String userId, String rawPw) {
        UserVO user = userMapper.findByUserId(userId);
        if (user == null || !passwordEncoder.matches(rawPw, user.getUserPw())) {
            return null;
        }
        return user;
    }
}
