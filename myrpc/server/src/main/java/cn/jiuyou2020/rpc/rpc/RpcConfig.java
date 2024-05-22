package cn.jiuyou2020.rpc.rpc;

import cn.jiuyou2020.nettransmit.MessageReceiver;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiuyou2020
 * @description:
 */
@Configuration
public class RpcConfig {
    @Resource
    private MessageReceiver messageReceiver;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            messageReceiver.run();
        };
    }
}
