package cn.jiuyou2020.rpc.service.impl;

import cn.jiuyou2020.rpc.apis.StockApi;
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
    private StockApi stockApi;

    @Override
    public String testGetParam(String stockId, int num) {
        return stockApi.testGetParam(stockId, num);
    }

    @Override
    public String testGet() {
        return stockApi.testGet();
    }

    @Override
    public String testPost() {
        return stockApi.testPost();
    }

    @Override
    public String testPostRequestBody(String requestBody) {
        return stockApi.testPostRequestBody(requestBody);
    }

    @Override
    public String testDeleteParam(String stockId) {
        return stockApi.testDeleteParam(stockId);
    }

    @Override
    public String testPut(String stockId) {
        return stockApi.testPut(stockId);
    }
}
