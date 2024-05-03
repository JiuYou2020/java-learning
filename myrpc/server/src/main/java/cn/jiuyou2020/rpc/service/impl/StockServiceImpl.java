package cn.jiuyou2020.rpc.service.impl;

import cn.jiuyou2020.rpc.service.StockService;
import org.springframework.stereotype.Service;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午8:09
 */
@Service
public class StockServiceImpl implements StockService {
    @Override
    public String reduceStock() {
        return "reduce stock success!";
    }
}
