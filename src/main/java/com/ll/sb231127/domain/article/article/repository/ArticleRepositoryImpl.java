package com.ll.sb231127.domain.article.article.repository;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ll.sb231127.domain.article.article.entity.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory; // QueryDSL 쿼리를 생성하기 위한 JPAQueryFactory 주입

    @Override
    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder(); // 조건을 동적으로 구성하기 위한 BooleanBuilder

        // 검색 조건 구성. 제목, 본문, 작성자 이름 중 사용자가 선택한 필터에 따라 쿼리 조건을 추가
        if (kwTypes.contains("authorUsername") && kwTypes.contains("title") && kwTypes.contains("body")) {
            builder.and(
                    article.title.containsIgnoreCase(kw)
                            .or(article.body.containsIgnoreCase(kw))
                            .or(article.author.username.containsIgnoreCase(kw))
            );
        }

        // QueryDSL을 사용하여 실제 쿼리 구성
        JPAQuery<Article> articlesQuery = jpaQueryFactory
                .select(article)
                .from(article)
                .where(builder);  // 여기서 builder의 조건을 적용

        // 페이지 요청에 따라 정렬 조건을 적용
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(article.getType(), article.getMetadata());
            articlesQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        // 페이지 요청에 따라 offset(시작점)과 limit(크기)을 설정
        articlesQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        // 총 개수를 계산하기 위한 별도의 쿼리 생성.
        JPAQuery<Long> totalQuery = jpaQueryFactory
                .select(article.count())
                .from(article)
                .where(builder);

        // 최종적으로 Page 객체를 생성하여 반환.
        // articlesQuery.fetch()는 데이터를 로드하고, totalQuery::fetchOne은 전체 개수를 계산
        return PageableExecutionUtils.getPage(articlesQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}