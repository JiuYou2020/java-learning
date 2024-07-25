package cn.jiuyou2020.elasticsearchentry.service;

import cn.jiuyou2020.elasticsearchentry.domain.Books;

/**
 * @author 金木
 * @description 针对表【books】的数据库操作Service
 * @createDate 2024-03-02 16:40:27
 */
public interface BooksService {

    void generateBooks();

    Books fuzzySearchByTitle(String title);

}
