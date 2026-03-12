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
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {
    // 依赖注入
    @Autowired
    private ArticleService articleService;
    
    @GetMapping
    public ResponseEntity<Page<Article>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Spring Data JPA 里常用的分页写法,它把页码和每页大小包装成一个分页对象
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(articleService.getAllArticles(pageable));
    }
    
    @GetMapping("/{id}")
    //@PathVariable Long id:表示从路径中取参数
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        //先把文章浏览量 +1
        articleService.incrementViewCount(id);
        //如果找到文章，就返回 200 OK + 文章数据;如果找不到，就返回 404 Not Found
//      return articleService.getArticleById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
        Optional<Article> optionalArticle = articleService.getArticleById(id);
        if (optionalArticle.isPresent()) {
            return ResponseEntity.ok(optionalArticle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
