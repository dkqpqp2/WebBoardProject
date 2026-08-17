package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.vo.BoardVO;
import com.example.board.vo.CommentVO;
import com.example.board.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;
    private final CommentService commentService;

    private static final Map<String, String> CATEGORY_MAP = new LinkedHashMap<>();
    static {
        CATEGORY_MAP.put("FREE", "자유게시판");
        CATEGORY_MAP.put("GAME", "게임");
        CATEGORY_MAP.put("SHOPPING", "쇼핑/할인정보");
        CATEGORY_MAP.put("IT", "IT/개발");
        CATEGORY_MAP.put("FINANCE", "재테크/투자");
        CATEGORY_MAP.put("REALESTATE", "부동산");
        CATEGORY_MAP.put("JOB", "취업/이직");
        CATEGORY_MAP.put("TRAVEL", "여행");
        CATEGORY_MAP.put("FOOD", "맛집/요리");
        CATEGORY_MAP.put("MOVIE", "영화/드라마");
        CATEGORY_MAP.put("MUSIC", "음악");
        CATEGORY_MAP.put("SPORTS", "스포츠");
        CATEGORY_MAP.put("PET", "반려동물");
        CATEGORY_MAP.put("BEAUTY", "뷰티/패션");
        CATEGORY_MAP.put("CAR", "자동차");
        CATEGORY_MAP.put("PARENTING", "육아");
        CATEGORY_MAP.put("HEALTH", "건강/헬스");
        CATEGORY_MAP.put("BOOK", "도서/독서");
        CATEGORY_MAP.put("STUDY", "학업/자격증");
        CATEGORY_MAP.put("HUMOR", "유머/짤방");
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/board/list";
    }

    private static final int PAGE_BLOCK = 10;

    @GetMapping("/board/list")
    public String list(@RequestParam(required = false) String category, @RequestParam(defaultValue = "1") int page, Model model) {
        List<BoardVO> boardList = (category == null || category.isBlank())
                ? boardService.getBoardList(page)
                : boardService.getBoardListByCategoryPaged(category, page);

        int totalPage = boardService.getTotalPage(category);
        int startPage = ((page - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;
        int endPage = Math.min(startPage + PAGE_BLOCK - 1, totalPage);

        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryMap", CATEGORY_MAP);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
    }

    @GetMapping("/board/detail/{boardSeq}")
    public String detail(@PathVariable int boardSeq, Model model) {
        model.addAttribute("board", boardService.getBoardDetail(boardSeq));
        model.addAttribute("commentList", commentService.getCommentList(boardSeq));
        model.addAttribute("categoryMap", CATEGORY_MAP);
        return "board/detail";
    }

    @GetMapping("/board/write")
    public String writeForm(Model model, HttpSession session) {
        if (getLoginUser(session) == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("categoryMap", CATEGORY_MAP);
        return "board/write";
    }

    @PostMapping("/board/write")
    public String write(@ModelAttribute BoardVO boardVO, HttpSession session) {
        UserVO loginUser = getLoginUser(session);
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        boardVO.setUserSeq(loginUser.getUserSeq());
        int boardSeq = boardService.insertBoard(boardVO);
        return "redirect:/board/detail/" + boardSeq;
    }

    @GetMapping("/board/edit/{boardSeq}")
    public String editForm(@PathVariable int boardSeq, Model model, HttpSession session) {
        BoardVO board = boardService.getBoardDetail(boardSeq);
        if (!isOwner(session, board)) {
            return "redirect:/board/detail/" + boardSeq;
        }
        model.addAttribute("board", board);
        model.addAttribute("categoryMap", CATEGORY_MAP);
        return "board/edit";
    }

    @PostMapping("/board/edit/{boardSeq}")
    public String edit(@PathVariable int boardSeq, @ModelAttribute BoardVO boardVO, HttpSession session) {
        if (!isOwner(session, boardService.getBoardDetail(boardSeq))) {
            return "redirect:/board/detail/" + boardSeq;
        }
        boardVO.setBoardSeq(boardSeq);
        boardService.updateBoard(boardVO);
        return "redirect:/board/detail/" + boardSeq;
    }

    @PostMapping("/board/delete/{boardSeq}")
    public String delete(@PathVariable int boardSeq, HttpSession session) {
        if (!isOwner(session, boardService.getBoardDetail(boardSeq))) {
            return "redirect:/board/detail/" + boardSeq;
        }
        boardService.deleteBoard(boardSeq);
        return "redirect:/board/list";
    }

    @PostMapping("/board/{boardSeq}/comment")
    public String addComment(@PathVariable int boardSeq, @ModelAttribute CommentVO commentVO, HttpSession session) {
        UserVO loginUser = getLoginUser(session);
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        commentVO.setBoardSeq(boardSeq);
        commentVO.setUserSeq(loginUser.getUserSeq());
        commentService.insertComment(commentVO);
        return "redirect:/board/detail/" + boardSeq;
    }

    @PostMapping("/board/{boardSeq}/comment/{commentSeq}/delete")
    public String deleteComment(@PathVariable int boardSeq, @PathVariable int commentSeq, HttpSession session) {
        if (getLoginUser(session) == null) {
            return "redirect:/user/login";
        }
        commentService.deleteComment(commentSeq);
        return "redirect:/board/detail/" + boardSeq;
    }

    private UserVO getLoginUser(HttpSession session) {
        return (UserVO) session.getAttribute("loginUser");
    }

    private boolean isOwner(HttpSession session, BoardVO board) {
        UserVO loginUser = getLoginUser(session);
        return loginUser != null && loginUser.getUserSeq() == board.getUserSeq();
    }
}
