package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcDecoder;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcEncoder;
import cn.jiuyou2020.reflectioncall.ReflectionCall;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyServer implements EnvironmentAware {
    private int port;
    private Environment environment;
    private static final Log LOG = LogFactory.getLog(NettyServer.class);

    public NettyServer() {
    }

    public void run() {
        setPort();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 在新线程中启动Netty服务器，以免阻塞Spring Boot的主线程
        executorService.submit(() -> {
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setPort() {
        if (!environment.containsProperty("rpc.port")) {
            throw new IllegalArgumentException("rpc.port must be set");
        }
        Integer serverPort = environment.getProperty("server.port", Integer.class);
        Integer property = environment.getProperty("rpc.port", Integer.class);
        if (Objects.equals(serverPort, property)) {
            throw new IllegalArgumentException("rpc.port must be different from server.port");
        }
        port = Objects.requireNonNull(property);
    }

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
                    ch.pipeline().addLast(new ServerHandler(new ReflectionCall()));
                }
            });

            // 启动服务器
            LOG.info("Netty server started on port " + port);
            b.bind(port).sync().channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
