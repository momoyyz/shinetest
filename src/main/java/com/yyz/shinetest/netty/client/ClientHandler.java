package com.yyz.shinetest.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;


public class ClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     *接收数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("netty 客户端接收到数据：" + msg);
        Random random=new Random();
         ctx.writeAndFlush("应答消息"+random.nextInt(100));

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("netty 客户端通道读取完毕！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("*****netty  client exceptionCaught****");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("******client channelActive");
//    }
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) {
//        System.out.println("******client handlerRemoved");
//    }
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) {
//        System.out.println("******client handlerAdded");
//    }
}
