package cn.jiuyou2020.rpc.controller;

import cn.jiuyou2020.rpc.apis.StockApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午8:14
 */
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockApi stockApi;

    @GetMapping("/testGetParam")
    public String reduceStock() {
        return stockApi.testGetParam("1", 1);
    }
}
