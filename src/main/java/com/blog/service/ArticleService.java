package com.blog.service;

import com.blog.model.Article;
import com.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ArticleService {
    
    @Autowired
    private ArticleRepository articleRepository;
    
    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findByIsPublishedTrue(pageable);
    }
    
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }
    
    @Transactional
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }
    
    @Transactional
    public Article updateArticle(Long id, Article article) {
        Article existing = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        
        existing.setTitle(article.getTitle());
        existing.setContent(article.getContent());
        existing.setSummary(article.getSummary());
        existing.setCoverImage(article.getCoverImage());
        existing.setIsPublished(article.getIsPublished());
        
        return articleRepository.save(existing);
    }
    
    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
    
    @Transactional
    public void incrementViewCount(Long id) {
        articleRepository.findById(id).ifPresent(article -> {
            article.setViewCount(article.getViewCount() + 1);
            articleRepository.save(article);
        });
    }
}
