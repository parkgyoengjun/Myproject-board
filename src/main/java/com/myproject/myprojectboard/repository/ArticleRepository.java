package com.myproject.myprojectboard.repository;

import com.myproject.myprojectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource // Spring rest 를 사용하기 위해
public interface ArticleRepository extends JpaRepository<Article, Long> {
}