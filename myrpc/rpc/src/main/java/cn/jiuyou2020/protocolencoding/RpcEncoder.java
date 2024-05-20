package cn.jiuyou2020.protocolencoding;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author: jiuyou2020
 * @description: 自定义协议编码器
 */
public class RpcEncoder extends MessageToMessageEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        RpcMessage message = new RpcMessage.Builder()
                .setSerializationType((byte) 1)
                .setIsHeartbeat(false)
                .setIsOneWay(false)
                .setIsResponse(false)
                .setStatusCode((byte) 0)
                .setMessageId(1)
                .setBodySize(msg.length)
                .setBody(msg)
                .build();
        ctx.writeAndFlush(message);
    }
}
