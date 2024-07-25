package cn.jiuyou2020.elasticsearchentry.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2024/3/3
 * <br>
 * {@code @Description: }
 */
@Configuration
public class ElasticsearchConfig {

    // URL and API key
    @Value("${elasticsearch.serveUrl}")
    private String serverUrl;

    @Value("${elasticsearch.apiKey}")
    private String apiKey;

    // 单例模式返回client
    private volatile ElasticsearchClient esClient;

    public ElasticsearchClient getEsClient() {
        if (esClient == null) {
            synchronized (ElasticsearchConfig.class) {
                if (esClient == null) {
                    esClient = createEsClient();
                }
            }
        }
        return esClient;
    }

    private ElasticsearchClient createEsClient() {
        // Create the low-level client
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();
        // Create the transport with a Jackson mapper
        JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, jsonpMapper);

        // And create the API client
        return new ElasticsearchClient(transport);
    }
}
