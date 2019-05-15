package com.onecoder.netty.demo4;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName: com.onecoder.netty.demo1.EchoServer.java
 * <p>@Description
 *
 *  解码器具体实现，用的比较多的是：（主要解决TCP底层的黏包和拆包问题）
 *  DelimiterBasedFrameDecoder：指定消息分割符的解码器，比如：1111&2222
 *  LineBasedFrameDecoder：以换行符为结束标志的解码器
 *  FixedLengthFrameDecoder: 固定长度的解码器
 *  LengthFieldBasedFrameDecoder: message = header+body ，基于长度解码的通用解码器
 *  StringDecoder: 文本解码，将接收到的消息转为字符串，一般会与上面几种进行组合，然后在后面加业务的handler
 * </p>
 * @Author hushiguo@onecoder.com.cn
 * @Date 2019-04-03 18:29
 **/
public class EchoServer {

    private int port;

    public  EchoServer(int port){
        this.port = port;
    }

    /**
     * @Author hushiguo
     * @Description 启动流程
     * @Date 2019/3/30 16:58
     * @Param []
     * @return void
     **/
    public void run(){
        //配置服务器线程组
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

       try{
           ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(boosGroup,workGroup)

                   //Channel,是客户端与服务端建立的一个连接通道
                   .channel(NioServerSocketChannel.class)

                   //存放已完成三次握手的请求的等待队列的最大长度
                   .option(ChannelOption.SO_BACKLOG,2048)

                   //ChannelHandler: 负责channel的逻辑处理
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel ch) {
                           /**
                            * LengthFieldBasedFrameDecoder 指定消息分割符的解码器，主要参数说明
                            * maxFrameLength: 数据包的最大长度
                            * lengthFieldOffset: 长度字段的偏移位，长度字段开始的地方，意思是跳过指定长度个字节之后才是消息体字段
                            * lengthFieldLength: 长度字段占的字节数，帧数据长度的字段本身长度
                            * lengthAdjustment: 一般header + body，添加到长度字段的补偿值，如果为负数，开发人员认为
                            * 这个header的长度是整个消息包的长度，则Netty应该减去对应的数字
                            * initialBytesToStrip: 从解码帧中第一次去除的字节数，获取完整的数据包之后，忽略前面指定位数的
                            * 长度字节，应用解码器拿到的就是不带长度域的数据包
                            * failFast: 是否快速失败
                            */
                           ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,
                                   2,0,0,true));
                            // 解决黏包，文本解码，将接收到的消息转为字符串
                            ch.pipeline().addLast(new StringDecoder());
                            // channelPipeline:负责管理ChannelHandler的有序容器
                            ch.pipeline().addLast(new Demo4ServerHandler());
                       }
                   });

           System.out.println(" Echo 服务器启动ing...");

           //绑定端口，同步等待成功
           ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

           //等待服务器监听端口关闭
           channelFuture.channel().closeFuture().sync();
       }catch (Exception e){
            e.printStackTrace();
       }finally {
           //优雅退出 释放资源
           boosGroup.shutdownGracefully();
           workGroup.shutdownGracefully();
       }

    }

    public static void main(String[] args){
        int port = 8080;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        new EchoServer(port).run();
    }

}
