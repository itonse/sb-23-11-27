package com.ll.sb231127.global.initData;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.service.ArticleService;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;  // initNotProdData 을 하려면 자기참조인 self를 통해 해야한다.
    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initNotProdData() {
        return args -> {
            self.work1();  // 자기자신의 프록시를 참조하여 메서드의 @Transactional 이 작동하도록 한다.
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        Member member1 = memberService.join("user1", "1234").getData();
        Member member2 = memberService.join("user2", "1234").getData();

        Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
        Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();

        Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
        Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();
    }

    @Transactional
    public void work2() {
        Member member1 = memberService.findById(1L).get();
        Article article1 = articleService.findById(1L).get();

        article1.addComment(member1, "댓글1");
        article1.addComment(member1, "댓글1");
    }
}
