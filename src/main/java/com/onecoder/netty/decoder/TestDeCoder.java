package com.onecoder.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * <p>Title: TestDeCoder</p>
 * <p>Description:
 *
 * 抽象解码器： ReplayingDecoder
 *
 * ReplayingDecoder继承ByteToMessageDecoder。
 * 不需要检查缓冲区是否有足够多的数据，速度略慢于ByteToMessageDecoder
 * 项目复杂性高时使用ReplayingDecoder，否则使用ByteToMessageDecoder</p>
 * <p>
 *     解码器具体实现，用的比较多的是：（主要解决TCP底层的黏包和拆包问题）
 *     DelimiterBasedFrameDecoder：指定消息分割符的解码器，比如：1111&2222
 *     LineBasedFrameDecoder：以换行符为结束标志的解码器
 *     FixedLengthFrameDecoder: 固定长度的解码器
 *     LengthFieldBasedFrameDecoder: message = header+body ，基于长度解码的通用解码器
 *     StringDecoder: 文本解码，将接收到的消息转为字符串，一般会与上面几种进行组合，然后在后面加业务的handler
 *
 * </p>
 * @author hushiguo
 * @date 2019/4/3 15:15
 */
public class TestDeCoder extends ReplayingDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

    }



}
