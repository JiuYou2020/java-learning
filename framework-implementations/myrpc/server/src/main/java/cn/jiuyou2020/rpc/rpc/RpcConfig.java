package cn.jiuyou2020.rpc.rpc;

import cn.jiuyou2020.nettransmit.MessageReceiver;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiuyou2020
 * @description:
 */
@Configuration
public class RpcConfig {

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            new MessageReceiver().run();
        };
    }
}
