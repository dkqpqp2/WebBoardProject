package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.vo.BoardVO;
import com.example.board.vo.CommentVO;
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

    @GetMapping("/board/list")
    public String list(@RequestParam(required = false) String category, Model model) {
        List<BoardVO> boardList = (category == null || category.isBlank())
                ? boardService.getBoardList()
                : boardService.getBoardListByCategory(category);

        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryMap", CATEGORY_MAP);
        model.addAttribute("selectedCategory", category);
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
    public String writeForm(Model model) {
        model.addAttribute("categoryMap", CATEGORY_MAP);
        return "board/write";
    }

    @PostMapping("/board/write")
    public String write(@ModelAttribute BoardVO boardVO) {
        int boardSeq = boardService.insertBoard(boardVO);
        return "redirect:/board/detail/" + boardSeq;
    }

    @GetMapping("/board/edit/{boardSeq}")
    public String editForm(@PathVariable int boardSeq, Model model) {
        model.addAttribute("board", boardService.getBoardDetail(boardSeq));
        model.addAttribute("categoryMap", CATEGORY_MAP);
        return "board/edit";
    }

    @PostMapping("/board/edit/{boardSeq}")
    public String edit(@PathVariable int boardSeq, @ModelAttribute BoardVO boardVO) {
        boardVO.setBoardSeq(boardSeq);
        boardService.updateBoard(boardVO);
        return "redirect:/board/detail/" + boardSeq;
    }

    @PostMapping("/board/delete/{boardSeq}")
    public String delete(@PathVariable int boardSeq) {
        boardService.deleteBoard(boardSeq);
        return "redirect:/board/list";
    }

    @PostMapping("/board/{boardSeq}/comment")
    public String addComment(@PathVariable int boardSeq, @ModelAttribute CommentVO commentVO) {
        commentVO.setBoardSeq(boardSeq);
        commentService.insertComment(commentVO);
        return "redirect:/board/detail/" + boardSeq;
    }

    @PostMapping("/board/{boardSeq}/comment/{commentSeq}/delete")
    public String deleteComment(@PathVariable int boardSeq, @PathVariable int commentSeq) {
        commentService.deleteComment(commentSeq);
        return "redirect:/board/detail/" + boardSeq;
    }
}
