package cn.learning.rabbitmq.orderly.producer;

import cn.learning.rabbitmq.orderly.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoProducerTest {


    @Autowired
    private DemoProducer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            for (int id = 0; id < 4; id++) {
                producer.syncSend(id);
            }
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
