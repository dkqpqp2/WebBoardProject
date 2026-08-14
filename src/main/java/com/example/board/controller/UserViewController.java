package com.example.board.controller;

import com.example.board.service.UserService;
import com.example.board.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserViewController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserVO userVO, Model model) {
        if (userService.isUserIdDuplicate(userVO.getUserId())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "user/signup";
        }
        userService.signup(userVO);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String userPw,
                         HttpSession session, Model model) {
        UserVO loginUser = userService.login(userId, userPw);
        if (loginUser == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "user/login";
        }
        session.setAttribute("loginUser", loginUser);
        return "redirect:/board/list";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/board/list";
    }
}
