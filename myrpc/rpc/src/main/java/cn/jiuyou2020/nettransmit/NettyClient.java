package cn.jiuyou2020.nettransmit;

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

public class NettyClient {
    private static final Log LOG = LogFactory.getLog(NettyServer.class);

    public byte[] connect(String url, byte[] serializedData) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = getBootstrap(group);
            // 连接到服务器
            URL parsedUrl = new java.net.URL(url);
            ChannelFuture f = bootstrap.connect(parsedUrl.getHost(), parsedUrl.getPort()).sync();
            // 创建一个ChannelPromise对象，用于异步操作的通知
            ChannelPromise promise = f.channel().newPromise();
            ClientHandler clientHandler = f.channel().pipeline().get(ClientHandler.class);
            clientHandler.setPromise(promise);
            // 构造一个RpcMessage对象
            RpcMessage message = new RpcMessage.Builder()
                    .setSerializationType((byte) 1)
                    .setIsHeartbeat(false)
                    .setIsOneWay(false)
                    .setIsResponse(false)
                    .setStatusCode((byte) 0)
                    .setMessageId(1)
                    .setBodySize(serializedData.length)
                    .setBody(serializedData)
                    .build();
            LOG.info("Sent message to server: " + message);
            f.channel().writeAndFlush(message);
            // 等待异步操作完成
            promise.await();
            // 返回异步操作的结果
            RpcMessage response = clientHandler.getResponse();
            // 等待连接关闭
            f.channel().closeFuture().sync();
            return response.getBody();
        } finally {
            group.shutdownGracefully();
        }
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
                    public void initChannel(SocketChannel ch) throws Exception {
                        // 向管道中添加一个IdleStateHandler，用于检测空闲状态
                        // readerIdleTime：读超时（秒），0表示不检测
                        // writerIdleTime：写超时（秒），4秒内没有写操作则触发IdleStateEvent
                        // allIdleTime：读或写超时（秒），0表示不检测
                        ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new RpcEncoder());
                        // 向管道中添加自定义的处理器ClientHandler，用于处理业务逻辑
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        return bootstrap;
    }
}
