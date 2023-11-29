package com.ll.sb231127.domain.article.articleTag.repository;

import com.ll.sb231127.domain.article.articleTag.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findByArticle_authorId(long authorId);

    List<ArticleTag> findByArticle_author_username(String username);
}
