package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.EnvContext;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcDecoder;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcEncoder;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiuyou2020
 * @description: 消息发送器, 用于与远程服务器建立连接并发送消息
 */
public class MessageSender {
    private static final Log LOG = LogFactory.getLog(MessageReceiver.class);

    /**
     * 构建并发送消息
     *
     * @param serializedData 序列化后的数据
     * @param f              ChannelFuture对象
     */
    private static void sendMessage(byte[] serializedData, ChannelFuture f) {
        // 构造一个RpcMessage对象
        RpcMessage message = new RpcMessage.Builder()
                .setSerializationType((byte) EnvContext.getSerializationType().getValue())
                .setIsHeartbeat(false)
                .setIsOneWay(false)
                .setIsResponse(false)
                .setStatusCode((byte) 0)
                .setMessageId(1)
                .setBodySize(serializedData.length)
                .setBody(serializedData)
                .build();
        LOG.info("发送数据消息: " + message);
        f.channel().writeAndFlush(message);
    }

    /**
     * 获取一个ChannelPromise对象，并给Handlers设置Promise
     *
     * @param f                     ChannelFuture对象
     * @param clientBusinessHandler 客户端业务处理器
     * @return ChannelPromise对象
     */
    private static ChannelPromise getPromise(ChannelFuture f, ClientBusinessHandler clientBusinessHandler) {
        // 创建一个ChannelPromise对象，用于异步操作的通知
        ChannelPromise promise = f.channel().newPromise();
        clientBusinessHandler.setPromise(promise);
        f.channel().pipeline().get(ClientHeartBeatHandler.class).setPromise(promise);
        f.channel().pipeline().get(ClientExceptionHandler.class).setPromise(promise);
        return promise;
    }

    /**
     * 获取配置好的Bootstrap实例
     *
     * @param group 事件循环组
     * @return Bootstrap实例
     */
    private static Bootstrap getBootstrap(EventLoopGroup group) {
        // 初始化一个Bootstrap实例，Netty客户端启动器
        Bootstrap bootstrap = new Bootstrap();

        // 配置Bootstrap使用的线程组，EventLoopGroup处理所有的事件（如连接、读、写事件）
        bootstrap.group(group)
                // 设置通道类型为NioSocketChannel，用于非阻塞传输的客户端通道类型
                .channel(NioSocketChannel.class)
                // 设置通道选项，SO_KEEPALIVE表示是否启用TCP Keep-Alive机制，保持长连接
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 设置用于初始化新连接的ChannelHandler
                .handler(new ChannelInitializer<SocketChannel>() {
                    // 当一个新的连接被接受时，这个方法会被调用
                    @Override
                    public void initChannel(SocketChannel ch) {
                        // 向管道中添加一个IdleStateHandler，用于检测空闲状态
                        // readerIdleTime：读超时（毫秒），1000表示在1秒内没有读操作则触发readIdle事件
                        // writerIdleTime：写超时（毫秒），0表示不检测
                        // allIdleTime：读或写超时（毫秒），0表示不检测
                        ch.pipeline().addLast(new IdleStateHandler(1000, 0, 0, TimeUnit.MILLISECONDS));
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new RpcEncoder());
                        // 向管道中添加自定义的处理器ClientHandler，用于处理业务逻辑
                        ch.pipeline().addLast(new ClientBusinessHandler());
                        ch.pipeline().addLast(new ClientHeartBeatHandler());
                        ch.pipeline().addLast(new ClientExceptionHandler());
                    }
                });
        return bootstrap;
    }

    /**
     * 连接到服务器
     *
     * @param url            服务器地址
     * @param serializedData 序列化后的数据
     * @return 服务器返回的数据
     * @throws Exception 连接异常
     */
    public byte[] connect(String url, byte[] serializedData) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = getBootstrap(group);
            // 连接到服务器
            String host = new URL(url).getHost();
            int port = EnvContext.getPort();
            ChannelFuture f = bootstrap.connect(host, port).sync();

            ClientBusinessHandler clientBusinessHandler = f.channel().pipeline().get(ClientBusinessHandler.class);
            ChannelPromise promise = getPromise(f, clientBusinessHandler);

            sendMessage(serializedData, f);
            // 等待异步操作完成
            promise.await();
            // 返回异步操作的结果
            RpcMessage response = clientBusinessHandler.getResponse();
            if (response == null) {
                throw new RuntimeException("发生异常，未收到响应消息");
            }
            // 等待连接关闭
            f.channel().closeFuture().sync();
            return response.getBody();
        } finally {
            group.shutdownGracefully();
        }
    }
}
