package cn.learning.rabbitmq.bus.quickstart.controller;

import cn.learning.rabbitmq.bus.quickstart.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/demo")
public class DemoController {


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ServiceMatcher busServiceMatcher;

    @GetMapping("/register")
    public String register(String username) {
        // ... 执行注册逻辑
        log.info("[register][执行用户({}) 的注册逻辑]", username);

        // ... 发布
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, busServiceMatcher.getBusId(),
                null, username));
        return "success";
    }


}
