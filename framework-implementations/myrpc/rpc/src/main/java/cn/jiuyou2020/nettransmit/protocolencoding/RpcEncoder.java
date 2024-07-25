package cn.jiuyou2020.nettransmit.protocolencoding;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: jiuyou2020
 * @description: 自定义协议编码器
 */
public class RpcEncoder extends MessageToByteEncoder<RpcMessage> {

    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) {
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
