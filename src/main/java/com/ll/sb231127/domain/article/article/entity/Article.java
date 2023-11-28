package com.ll.sb231127.domain.article.article.entity;

import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder   // 슈퍼빌더 사용하기 위해서는 아래 두 어노테이션이 필수
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)   // id 까지 문자열로 나오게 하려면 callSuper true로 설정
public class Article extends BaseEntity {
    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
}
