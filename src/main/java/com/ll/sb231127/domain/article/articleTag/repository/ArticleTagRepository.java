package com.ll.sb231127.domain.article.articleTag.repository;

import com.ll.sb231127.domain.article.articleTag.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findByAuthorId(long authorId);

    List<ArticleTag> findByAuthor_username(String username);
}
