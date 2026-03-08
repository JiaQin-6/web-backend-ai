package com.blog.controller;

import com.blog.model.Article;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping
    public ResponseEntity<Page<Article>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(articleService.getAllArticles(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        articleService.incrementViewCount(id);
        return articleService.getArticleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        return ResponseEntity.ok(articleService.createArticle(article));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id, 
            @RequestBody Article article) {
        return ResponseEntity.ok(articleService.updateArticle(id, article));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
