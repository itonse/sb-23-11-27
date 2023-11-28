package com.ll.sb231127.domain.article.article.entity;

import com.ll.sb231127.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
}
