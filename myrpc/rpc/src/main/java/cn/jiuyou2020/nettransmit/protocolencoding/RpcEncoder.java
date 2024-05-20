package cn.jiuyou2020.nettransmit.protocolencoding;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author: jiuyou2020
 * @description: 自定义协议编码器
 */
public class RpcEncoder extends MessageToByteEncoder<RpcMessage> {

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

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) throws Exception {
        out.writeShort(RpcMessage.MAGIC);
        out.writeShort(RpcMessage.HEADER_SIZE);
        out.writeShort(RpcMessage.VERSION);
        out.writeByte(msg.getSerializationType());
        out.writeBoolean(msg.isHeartbeat());
        out.writeBoolean(msg.isOneWay());
        out.writeBoolean(msg.isResponse());
        out.writeByte(msg.getStatusCode());
        out.writeShort(msg.getReserved());
        out.writeInt(msg.getMessageId());
        out.writeInt(msg.getBodySize());
        out.writeBytes(msg.getBody());
    }
}
