package com.onecoder.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * <p>Title: MessageDecoder</p>
 * <p>Description:
 *
 * 抽象解码器： MessageToMessageDecoder  用于从一种消息解码到另一种消息（例如：POJO到POJO）</p>
 *
 * @author hushiguo
 * @date 2019/4/3 15:17
 */
public class MessageDecoder extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) {

    }


}
