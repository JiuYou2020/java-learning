package cn.jiuyou2020.annonation;

import cn.jiuyou2020.nettransmit.NettyServer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jiuyou2020
 * @description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(NettyServer.class)
public @interface EnableRpcServer {
}
