package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcDecoder;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: jiuyou2020
 * @description: 客户端心跳处理器，用于处理客户端的心跳消息，如果是异常消息，直接调用下一个处理器
 */
class MessageTestReceiver {
    private static final Log LOG = LogFactory.getLog(MessageTestReceiver.class);

    private int port;

    /**
     * 设置port属性，从配置文件中获取
     */
    @PostConstruct
    private void setPort() {
        port = 8088;
    }

    /**
     * 在新线程中启动Netty服务器，以免阻塞Spring Boot的主线程
     */
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                startServer();
                LOG.info("Netty服务启动成功,端口：" + port);
            } catch (Exception e) {
                LOG.error("Netty服务启动失败", e);
            }
        });
    }


    /**
     * 启动Netty服务器
     *
     * @throws InterruptedException 线程中断异常
     */
    private void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new RpcDecoder());
                    ch.pipeline().addLast(new RpcEncoder());
                    ch.pipeline().addLast(new ServerTestHandler());
                }
            });

            // 启动服务器
            LOG.info("Netty 服务启动成功： " + port);
            b.bind(port).sync().channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
