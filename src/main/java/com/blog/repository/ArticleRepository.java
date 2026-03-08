package com.blog.repository;

import com.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByIsPublishedTrue(Pageable pageable);
    
    @Query("SELECT a FROM Article a WHERE a.isPublished = true ORDER BY a.viewCount DESC")
    Page<Article> findTopArticles(Pageable pageable);
}
