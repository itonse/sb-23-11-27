package com.ll.sb231127.global.jpa.baseEntity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass   // 상속받는 엔티티의 필드를 컬럼으로 인식하게 함
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder  // 빌더패턴에서 부모 엔티티와 자식 엔티티를 하나의 빌더로 묶으려면 사용
@NoArgsConstructor(access = PROTECTED)   // 위의 슈퍼빌더를 사용하기 위해 필요
@Getter
@EqualsAndHashCode
@ToString
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    @Setter
    private LocalDateTime modifyDate;
}
