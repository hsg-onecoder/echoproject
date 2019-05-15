package com.onecoder.netty.echo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author hushiguo
 * @Description echo服务端
 * @Date 2019/3/30 16:58
 * @Param
 * @return
 **/
public class EchoServer {

    private int port;

    public  EchoServer(int port){
        this.port = port;
    }

    /**
     * @Description 启动流程
     * @Param
     * @return
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
                            //channelPipeline:负责管理ChannelHandler的有序容器
                            ch.pipeline().addLast(new EchoServerHandler());
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
