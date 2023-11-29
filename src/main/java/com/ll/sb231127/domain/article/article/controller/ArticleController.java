package com.ll.sb231127.domain.article.article.controller;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model
    ) {
        List<Sort.Order> sorts = new ArrayList<>();   // 정렬 순서를 지정하기 위한 리스트 생성
        sorts.add(Sort.Order.desc("id"));   // ID 기준 내림차순 정렬 추가

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // 페이지 요청 정보 생성, 한 페이지당 10개의 항목

        Page<Article> itemsPage = articleService.search(pageable);   // 페이지 정보를 기반으로 게시글 검색
        model.addAttribute("itemsPage", itemsPage);   // 모델에 검색된 페이지 정보 추가

        return "article/list";
    }
}
