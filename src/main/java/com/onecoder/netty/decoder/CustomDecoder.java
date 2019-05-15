package com.onecoder.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * <p>Title: CustomDecoder</p>
 * <p>Description:
 *
 * 抽象解码器：ByteToMessageDecoder 用于将字节转为消息，需要检查缓冲区是否有足够的字节</p>
 *
 * @author hushiguo
 * @date 2019/4/3 15:09
 */
public class CustomDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //int 是4个字节，需要检测下是否满足
        if(in.readableBytes() >= 4){
            //添加到解码信息里面去
            out.add(in.readInt());
        }

    }


}
