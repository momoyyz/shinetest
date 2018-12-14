package com.yyz.shinetest.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * Title: NettyClientHandler
 * Description: 客户端业务逻辑实现
 * Version:1.0.0
 *
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // super.channelActive(ctx);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//        System.out.println("客户端接收的消息: " + s);
//        channelHandlerContext.writeAndFlush("客户端已经接收到消息\n");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client channelRead");
        System.out.println("客户端接收的消息: " + msg.toString());
        ctx.writeAndFlush("客户端已经接收到消息\n");
        //super.channelRead(ctx, msg);
    }
}
