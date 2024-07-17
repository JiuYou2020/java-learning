package cn.southtang.test;

import cn.southtang.Application;
import cn.southtang.producer.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class DemoTest {


    @Autowired
    private OrderService service;


    @Test
    public void testSyncSend02() throws InterruptedException {
        service.createOrder("order");
        log.info("[testSyncSendDelay][发送编号：[{}] 发送成功]", "order");
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
