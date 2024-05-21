package cn.jiuyou2020.rpc.service.impl;

import cn.jiuyou2020.rpc.apis.StockService;
import cn.jiuyou2020.rpc.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午5:45
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StockService stockService;

    @Override
    public String testGetParam(String stockId, int num) {
        return stockService.testGetParam(stockId, num);
    }

    @Override
    public String testGet() {
        return stockService.testGet();
    }

    @Override
    public String testPost() {
        return stockService.testPost();
    }

    @Override
    public String testPostRequestBody(String requestBody) {
        return stockService.testPostRequestBody(requestBody);
    }

    @Override
    public String testDeleteParam(String stockId) {
        return stockService.testDeleteParam(stockId);
    }

    @Override
    public String testPut(String stockId) {
        return stockService.testPut(stockId);
    }
}
