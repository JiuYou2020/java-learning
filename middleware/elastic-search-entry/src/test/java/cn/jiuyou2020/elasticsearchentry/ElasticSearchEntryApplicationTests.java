package cn.jiuyou2020.elasticsearchentry;

import cn.jiuyou2020.elasticsearchentry.config.ElasticsearchConfig;
import cn.jiuyou2020.elasticsearchentry.domain.Books;
import cn.jiuyou2020.elasticsearchentry.tools.BookDataGenerator;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class ElasticSearchEntryApplicationTests {
    @Autowired
    private ElasticsearchConfig elasticsearchConfig;

    @Test
    void contextLoads() {
    }

    @Test
    void testUploadBooksToElasticSearch() throws IOException {
        List<Books> books = BookDataGenerator.generateBookData(1);
        books.get(0).setId(1);
        ElasticsearchClient esClient = elasticsearchConfig.getEsClient();
        BulkRequest.Builder builder = new BulkRequest.Builder();
        for (Books book : books) {
            builder.operations(op -> op
                    .index(idx -> idx
                            .index("books")
                            .id(String.valueOf(book.getId()))
                            .document(book)));
        }
        BulkResponse result = esClient.bulk(builder.build());

        if (result.errors()) {
            System.out.println("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            }
        }
    }

    @Test
    void testGetBooks() throws IOException {
        ElasticsearchClient esClient = elasticsearchConfig.getEsClient();
        //查找id为1的书籍
        GetResponse<Books> books = esClient.get(g -> g
                        .index("books")
                        .id("1"),
                Books.class);
        if (books.found()) {
            System.out.println(books.source());
        } else {
            System.out.println("Not found");
        }
    }

    @Test
    void testFuzzySearchBooks() throws IOException {
        ElasticsearchClient esClient = elasticsearchConfig.getEsClient();
        //模糊查找描述中包含“Ev”的书籍
        Query query = WildcardQuery.of(w -> w
                .field("description")
                .wildcard("*Ev*"))._toQuery();
        SearchResponse<Books> search1 = esClient.search(s -> s
                .index("books")
                .query(query), Books.class);
        System.out.println(search1.hits().hits());


        SearchResponse<Books> search = esClient.search(s -> s
                .index("books")
                .query(q -> q
                        .match(t -> t
                                .field("description")
                                .query("BWJekSXGAthdClsKnOnEvHYShRTiNPPgWDOuCuiwGOJUrFbrLq"))), Books.class);
        System.out.println(search.hits().hits());
    }

}
