package cn.jiuyou2020.rpc.apis;

import cn.jiuyou2020.annonation.RemoteService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午8:19
 */
@RemoteService(url = "http://localhost", name = "demo")
public interface StockService {
    @GetMapping("/testGetParam")
    String testGetParam(String stockId, int num);

    @GetMapping("/testGet")
    String testGet();

    @GetMapping("/testPost")
    String testPost();

    @GetMapping("/testPostRequestBody")
    String testPostRequestBody(String requestBody);

    @GetMapping("/testDeleteParam")
    String testDeleteParam(String stockId);

    @GetMapping("/testPut")
    String testPut(String stockId);
}
