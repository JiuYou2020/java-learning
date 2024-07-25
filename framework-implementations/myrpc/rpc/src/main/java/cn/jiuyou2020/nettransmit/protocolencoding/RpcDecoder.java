package cn.jiuyou2020.nettransmit.protocolencoding;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: jiuyou2020
 * @description: 自定义协议解码器
 */
@SuppressWarnings("unused")
public class RpcDecoder extends ByteToMessageDecoder {

    /**
     * 在Netty的ByteToMessageDecoder中，decode方法的参数out是一个输出列表，用于存储解码后的消息。
     * <p>
     * 在这段代码中，decode方法从输入的ByteBuf中读取并解码出一个RpcMessage对象，
     * 然后将这个对象添加到out列表中。这样，解码后的消息就可以被后续的ChannelHandler处理。
     * <p>
     * 当decode方法执行完毕，Netty会检查out列表，如果列表中有元素，Netty会将这些元素传递给
     * ChannelPipeline中的下一个ChannelHandler进行处理。如果out列表为空，
     * 那么Netty会认为当前的decode方法没有解码出任何消息，不会触发下一个ChannelHandler。
     * <p>
     * 因此，向out列表中添加元素的目的是将解码后的消息传递给ChannelPipeline中的
     * 下一个ChannelHandler。
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in  the {@link ByteBuf} from which to read data
     * @param out the {@link List} to which decoded messages should be added
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 检查是否有足够的数据来读取消息头
        if (in.readableBytes() < RpcMessage.HEADER_SIZE) {
            return;
        }

        // 标记当前读指针的位置
        in.markReaderIndex();

        // 读取魔术位
        short magic = in.readShort();
        if (magic != RpcMessage.MAGIC) {
            // 不是预期的魔术位，关闭连接
            ctx.close();
            return;
        }

        // 读取消息头长度
        short headerSize = in.readShort();
        if (headerSize != RpcMessage.HEADER_SIZE) {
            // 消息头长度不符合预期，关闭连接
            ctx.close();
            return;
        }

        // 读取协议版本
        short version = in.readShort();

        // 读取消息体序列化类型
        byte serializationType = in.readByte();

        // 读取心跳标记
        boolean isHeartbeat = in.readBoolean();

        // 读取单向消息标记
        boolean isOneWay = in.readBoolean();

        // 读取响应消息标记
        boolean isResponse = in.readBoolean();

        // 读取响应消息状态码
        byte statusCode = in.readByte();

        // 读取保留字段
        short reserved = in.readShort();

        // 读取消息ID
        int messageId = in.readInt();

        // 读取消息体长度
        int bodySize = in.readInt();

        // 检查是否有足够的数据来读取消息体
        if (in.readableBytes() < bodySize) {
            // 没有足够的数据来读取消息体，重置读指针
            in.resetReaderIndex();
            return;
        }

        // 读取消息体
        byte[] body = new byte[bodySize];
        in.readBytes(body);

        // 创建消息对象并添加到输出列表
        RpcMessage message = new RpcMessage.Builder()
                .setSerializationType(serializationType)
                .setIsHeartbeat(isHeartbeat)
                .setIsOneWay(isOneWay)
                .setIsResponse(isResponse)
                .setStatusCode(statusCode)
                .setReserved(reserved)
                .setMessageId(messageId)
                .setBodySize(bodySize)
                .setBody(body)
                .build();
        out.add(message);

    }
}
