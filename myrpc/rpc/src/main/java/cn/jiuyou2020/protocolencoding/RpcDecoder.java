package cn.jiuyou2020.protocolencoding;

import cn.jiuyou2020.serialize.SerializationDataOuterClass;
import cn.jiuyou2020.serialize.SerializationFacadeImpl;
import cn.jiuyou2020.serialize.strategy.ProtobufSerializationStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author: jiuyou2020
 * @description: 自定义协议解码器
 */
public class RpcDecoder extends MessageToMessageDecoder<byte[]> {

//    @Override
//    protected void decode(ChannelHandlerContext ctx, RpcMessage msg, List<Object> out) throws Exception {
//        byte[] body = msg.getBody();
//        SerializationFacadeImpl serializationFacade = new SerializationFacadeImpl(new ProtobufSerializationStrategy());
//        SerializationDataOuterClass.SerializationData deserialize = serializationFacade.deserialize(body, SerializationDataOuterClass.SerializationData.class);
//        System.out.println(deserialize);
//        ctx.writeAndFlush(deserialize.toString());
//    }

    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        System.out.println(111);
    }
}
