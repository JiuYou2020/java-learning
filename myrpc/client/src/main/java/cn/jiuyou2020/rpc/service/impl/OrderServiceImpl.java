package cn.jiuyou2020.rpc.service.impl;

import cn.jiuyou2020.rpc.service.OrderService;
import cn.jiuyou2020.rpc.apis.StockApi;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
}
