package com.onecoder.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <p>Title: TestEncoder</p>
 * <p>Description:
 *  消息转换为字节数组，调用write方法，会先判断当前解码器是否支持需要发送的消息类型，如果不支持，则透传。
 * </p>
 *
 * @author hushiguo
 * @date 2019/4/3 15:35
 */
public class TestEncoder1 extends MessageToByteEncoder<Integer>{


    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) {
        out.writeInt(msg);
    }


}
