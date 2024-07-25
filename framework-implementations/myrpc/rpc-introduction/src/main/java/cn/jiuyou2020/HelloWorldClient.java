package cn.jiuyou2020;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.buffer.Unpooled;

public class HelloWorldClient {

    private final String host;
    private final int port;

    public HelloWorldClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloWorldClient("localhost", 8080).start(); // 启动客户端，连接到localhost的8080端口
    }

    public void start() throws InterruptedException {
        // 创建EventLoopGroup实例来处理客户端事件
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Bootstrap实例来设置客户端
            Bootstrap b = new Bootstrap();
            b.group(group) // 设置EventLoopGroup
                    .channel(NioSocketChannel.class) // 指定使用NioSocketChannel来作为客户端通道
                    .handler(new ChannelInitializer<SocketChannel>() { // 设置通道初始化处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO)); // 添加日志处理器
                            p.addLast(new HelloWorldClientHandler()); // 添加自定义的处理器
                        }
                    });

            // 连接到服务端
            ChannelFuture f = b.connect(host, port).sync();
            // 发送消息到服务端
            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello, World!".getBytes()));
            // 等待客户端通道关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭EventLoopGroup，释放所有资源
            group.shutdownGracefully();
        }
    }
}

class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 当接收到服务端的消息时，打印消息
        System.out.println("Received message from server: " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 捕捉异常并打印堆栈跟踪，关闭上下文
        cause.printStackTrace();
        ctx.close();
    }
}