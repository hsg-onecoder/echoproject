package com.onecoder.netty.demo1;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
                            // channelPipeline:负责管理ChannelHandler的有序容器
                            ch.pipeline().addLast(new Demo1ServerHandler());
                       }
                   });

           System.out.println(" Echo 服务器启动ing...");

           //绑定端口，同步等待成功
           ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

           //等待服务器监听端口关闭
           channelFuture.channel().closeFuture().sync();
       }catch (Exception e){
            System.out.println(e);
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
