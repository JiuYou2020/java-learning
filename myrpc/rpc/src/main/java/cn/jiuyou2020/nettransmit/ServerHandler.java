package cn.jiuyou2020.nettransmit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Arrays;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(111);
        if (msg instanceof byte[]) {
            byte[] data = (byte[]) msg;
            System.out.println("Server received byte array: " + Arrays.toString(data));
            ctx.writeAndFlush("Byte array received: " + Arrays.toString(data));
        } else if (msg instanceof String) {
            String message = (String) msg;
            System.out.println("Server received message: " + message);
            ctx.writeAndFlush("Message received: " + message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
