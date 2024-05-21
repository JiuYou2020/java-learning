package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import cn.jiuyou2020.reflectioncall.ReflectionCall;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    // 反射调用
    private final ReflectionCall reflectionCall;

    public ServerHandler(ReflectionCall reflectionCall) {
        this.reflectionCall = reflectionCall;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        Object call = reflectionCall.call(rpcMessage);
        int serializationType = rpcMessage.getSerializationType();
        RpcResponse rpcResponse = RpcResponseFactory.getFactory(serializationType).createRpcResponse(call);
        byte[] respData = SerializationFacade.getStrategy(serializationType).serialize(rpcResponse);
        // 构建返回消息
        RpcMessage resp = new RpcMessage.Builder()
                .setSerializationType(rpcMessage.getSerializationType())
                .setIsHeartbeat(false)
                .setIsOneWay(false)
                .setIsResponse(true)
                .setStatusCode((byte) 0)
                .setMessageId(rpcMessage.getMessageId())
                .setBodySize(respData.length)
                .setBody(respData)
                .build();
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        throw new RuntimeException(cause);
    }
}
