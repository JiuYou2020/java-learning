package cn.jiuyou2020.rpc.controller;

import cn.jiuyou2020.rpc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiuyou2020
 * @description 订单controller
 * @date 2024/4/24 下午5:41
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/placeAnOrder")
    public String reduceStock() {
        return orderService.placeAnOrder();
    }
}
