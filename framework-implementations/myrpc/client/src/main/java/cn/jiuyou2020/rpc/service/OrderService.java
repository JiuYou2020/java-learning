package cn.jiuyou2020.rpc.service;

/**
 * @author jiuyou2020
 * @description 订单service
 * @date 2024/4/24 下午5:45
 */
public interface OrderService {
    String testGetParam(String stockId, int num);

    String testGet();

    String testPost();

    String testPostRequestBody(String requestBody);

    String testDeleteParam(String stockId);

    String testPut(String stockId);
}
