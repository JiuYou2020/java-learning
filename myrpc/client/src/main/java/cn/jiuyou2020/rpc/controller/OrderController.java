package cn.jiuyou2020.rpc.controller;

import cn.jiuyou2020.rpc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/testGetParam/{stockId}/{num}")
    public String testGetParam(@PathVariable String stockId, @PathVariable int num) {
        return orderService.testGetParam(stockId, num);
    }

    @GetMapping("/testGet")
    public String testGet() {
        return orderService.testGet();
    }

    @PostMapping("/testPost")
    public String testPost() {
        return orderService.testPost();
    }

    @PutMapping("/testPostRequestBody")
    public String testPostRequestBody(@RequestBody String requestBody) {
        return orderService.testPostRequestBody(requestBody);
    }

    //Delete，有一个参数
    @DeleteMapping("/testDeleteParam")
    public String testDeleteParam(@PathVariable String stockId) {
        return orderService.testDeleteParam(stockId);
    }

    //Put，有一个参数
    @PutMapping("/testPut")
    public String testPut(@PathVariable String stockId) {
        return orderService.testPut(stockId);
    }

}
