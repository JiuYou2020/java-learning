package cn.jiuyou2020.elasticsearchentry.service.impl;

import cn.jiuyou2020.elasticsearchentry.config.ElasticsearchConfig;
import cn.jiuyou2020.elasticsearchentry.domain.Books;
import cn.jiuyou2020.elasticsearchentry.mapper.BooksMapper;
import cn.jiuyou2020.elasticsearchentry.service.BooksService;
import cn.jiuyou2020.elasticsearchentry.tools.BookDataGenerator;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {
    @Autowired
    private BooksMapper booksMapper;
    @Autowired
    private ElasticsearchConfig elasticsearchConfig;

    @Override
    public void generateBooks() {
        List<Books> books = BookDataGenerator.generateBookData(100000);
        booksMapper.insertBooksBatch(books);
    }

    @Override
    public Books fuzzySearchByTitle(String title) {
        ElasticsearchClient esClient = elasticsearchConfig.getEsClient();
        return null;
    }
}
