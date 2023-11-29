package com.ll.sb231127.domain.article.articleTag.entity;


import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class ArticleTag extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Article article; // article_id
    private String content;
}