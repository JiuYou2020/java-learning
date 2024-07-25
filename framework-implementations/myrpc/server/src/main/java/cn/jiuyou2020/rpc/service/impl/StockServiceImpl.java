package cn.jiuyou2020.rpc.service.impl;

import cn.jiuyou2020.rpc.apis.StockService;
import org.springframework.stereotype.Service;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午8:09
 */
@Service
public class StockServiceImpl implements StockService {
    @Override
    public String testGetParam(String stockId, int num) {
        return "Hello RPC";
    }

    @Override
    public String testGet() {
        return "";
    }

    @Override
    public String testPost() {
        return "";
    }

    @Override
    public String testPostRequestBody(String requestBody) {
        return "";
    }

    @Override
    public String testDeleteParam(String stockId) {
        return "";
    }

    @Override
    public String testPut(String stockId) {
        return "";
    }
}
