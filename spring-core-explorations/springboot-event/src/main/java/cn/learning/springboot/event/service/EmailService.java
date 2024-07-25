package cn.learning.springboot.event.service;

import cn.learning.springboot.event.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService implements ApplicationListener<UserRegisterEvent> {


    @Override
    @Async
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("[onApplicationEvent][给用户({}) 发送邮件]", event.getUsername());
    }

}
