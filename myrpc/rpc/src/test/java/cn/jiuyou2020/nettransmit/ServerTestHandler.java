package cn.jiuyou2020.nettransmit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: jiuyou2020
 * @description: 服务器测试处理器
 */
public class ServerTestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.writeAndFlush(msg);
    }
}
