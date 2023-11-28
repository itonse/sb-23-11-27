package com.ll.sb231127.domain.article.article.sevice;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.service.ArticleService;
import com.ll.sb231127.domain.article.articleComment.entity.ArticleComment;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.rsData.RsData;
import com.ll.sb231127.standard.util.Ut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @DisplayName("글 쓰기")
    @Test
    void t1() {
        RsData<Article> writeRs = articleService.write(1, "제목", "내용");
        Article article = writeRs.getData();

        assertThat(article.getId()).isGreaterThan(0L);
    }

    @DisplayName("1번 글을 가져온다.")
    @Test
    void t2() {
        Article article = articleService.findById(1L).get();
        assertThat(article.getTitle()).isEqualTo("제목1");
    }

    @DisplayName("1번 글의 작성자의 username이 user1 이다.")
    @Test
    void t3() {
        Article article = articleService.findById(1L).get();
        Member author = article.getAuthor();

        assertThat(author.getUsername()).isEqualTo("user1");
    }

    @DisplayName("1번 글의 제목을 수정한다.")
    @Test
    void t4() {
        Article article = articleService.findById(1L).get();

        Ut.thread.sleep(1000);

        articleService.modify(article, "수정된 제목", "수정된 내용");

        assertThat(article.getTitle()).isEqualTo("수정된 제목");
    }

    @DisplayName("1번 글의 댓글들을 수정한다.")
    @Test
    @Rollback(false)  // 테스트DB에서 확인하기 위해 붙임
    void t5() {
        Article article = articleService.findById(1L).get();

        article.getComments().forEach(comment -> {
            articleService.modifyComment(comment, comment.getBody() + "!!");
        });
    }

    @DisplayName("1번 글의 댓글 중 마지막 것을 삭제한다.")
    @Test
    @Rollback(false)  // 테스트DB에서 확인하기 위해 붙임
    void t6() {
        Article article = articleService.findById(1L).get();

        ArticleComment lastComment = article.getComments().getLast();

        article.getComments().remove(lastComment);  // JPA 가 연결을 끊어서 고아 객체가 된다
    }
}
