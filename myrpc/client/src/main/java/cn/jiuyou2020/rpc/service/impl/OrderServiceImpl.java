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
    public String placeAnOrder() {
        return stockApi.reduceStock();
    }

    @Override
    public String getUser() {
        return stockApi.getUser();
    }
}
