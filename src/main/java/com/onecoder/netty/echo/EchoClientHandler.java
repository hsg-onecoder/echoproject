package com.onecoder.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Description TODO
 * @Param  
 * @return 
 * @Author hushiguo@onecoder.com.cn 
 * @Date 2019/3/30 17:34
 **/
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {

        System.out.println(" Client received :" + msg.toString(CharsetUtil.UTF_8));

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" Client channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * @Description TODO
     * @Param [ctx] 
     * @return void
     * @Author hushiguo@onecoder.com.cn 
     * @Date 2019/3/30 17:34
     **/
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * @Author hushiguo
     * @Description channel已经创建，但是未注册到一个EventLoop里面，也就是没有和Selector绑定
     * @Date 2019/3/30 17:08
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * @Author hushiguo
     * @Description 变为活跃状态(连接到了远程主机)，可以接收和发送数据
     * @Date 2019/3/30 17:09
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" Client channelActive");
        ctx.writeAndFlush(Unpooled.copiedBuffer(" hello baby",CharsetUtil.UTF_8));
    }

    /**
     * @Author hushiguo
     * @Description channel处于非活跃状态(没有连接到了远程主机)
     * @Date 2019/3/30 17:10
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
