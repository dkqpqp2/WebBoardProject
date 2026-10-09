package com.example.board.service;

import com.example.board.mapper.UserMapper;
import com.example.board.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserService userService;

    @BeforeEach
    void setUp(){
        userService = new UserService(userMapper, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입하면 비밀번호가 암호화돼서 저장")
    void signupEncodePassword(){
        UserVO user = new UserVO();
        user.setUserPw("pw1234");

        userService.signup(user);

        verify(userMapper).insertUser(user);
        assertNotEquals("pw1234", user.getUserPw());
        assertTrue(passwordEncoder.matches("pw1234", user.getUserPw()));
    }

    @Test
    @DisplayName("비밀번호가 맞으면 로그인 성공")
    void loginSuccess(){
        UserVO user = new UserVO();
        user.setUserPw(passwordEncoder.encode("pw1234"));
        when(userMapper.findByUserId("hong")).thenReturn(user);

        UserVO result = userService.login("hong", "pw1234");

        assertSame(user, result);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인 실패")
    void loginFail(){
        UserVO user = new UserVO();
        user.setUserPw(passwordEncoder.encode("pw1234"));
        when(userMapper.findByUserId("hong")).thenReturn(user);

        UserVO result = userService.login("hong", "wrong");

        assertNull(result);
    }
}
