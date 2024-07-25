package cn.learning.rabbitmq.cloud.quickstart.producer.controller;

import cn.learning.rabbitmq.cloud.quickstart.producer.message.Demo01Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/demo01")
public class Demo01Controller {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/send")
    public boolean send() {
        // 创建 Message
        Demo01Message message = new Demo01Message()
                .setId(new Random().nextInt());
        // 创建 Spring Message 对象,使用函数式变成模型的方式而不是加上已弃用的@enableBinding/@output注解,demo01-out-0是配置文件中的spring.cloud.stream.bindings.demo01-out-0
        return streamBridge.send("demo01-out-0", MessageBuilder.withPayload(message).build());
    }
}
