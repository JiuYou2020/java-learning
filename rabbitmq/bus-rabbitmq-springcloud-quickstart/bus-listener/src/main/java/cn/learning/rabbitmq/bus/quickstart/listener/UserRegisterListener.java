package cn.learning.rabbitmq.bus.quickstart.listener;

import cn.learning.rabbitmq.bus.quickstart.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 用户注册事件的监听器
 */
@Component
@Slf4j
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {


    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("[onApplicationEvent][监听到用户({}) 注册]", event.getUsername());
    }

}
