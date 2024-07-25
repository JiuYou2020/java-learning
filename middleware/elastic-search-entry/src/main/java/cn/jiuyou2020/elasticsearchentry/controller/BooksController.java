package cn.jiuyou2020.elasticsearchentry.controller;

import cn.jiuyou2020.elasticsearchentry.domain.Books;
import cn.jiuyou2020.elasticsearchentry.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2024/3/2
 * <br>
 * {@code @Description: }
 */
@RestController
@RequestMapping("/api/book")
public class BooksController {
    @Autowired
    private BooksService booksService;

    @PostMapping("/generateBooks")
    public void generateBooks() {
        booksService.generateBooks();
    }

    @GetMapping("/fuzzySearchByTitle")
    public Books fuzzySearchByTitle(String title) {
        return booksService.fuzzySearchByTitle(title);
    }
}
