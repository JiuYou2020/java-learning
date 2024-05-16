package cn.jiuyou2020.rpc.apis;

import cn.jiuyou2020.annonation.RemoteService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午8:19
 */
@RemoteService(url = "http://localhost:8080", name = "demo")
public interface StockApi {
    @GetMapping("/stock/reduce")
    String reduceStock();

    @GetMapping("/getUser")
    String getUser();
}
