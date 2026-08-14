package com.example.board.controller;

import com.example.board.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loginUser")
    public UserVO loginUser(HttpSession session) {
        return (UserVO) session.getAttribute("loginUser");
    }
}
