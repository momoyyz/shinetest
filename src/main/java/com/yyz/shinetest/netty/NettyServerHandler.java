package com.yyz.shinetest.netty;

import com.netty.controller.NettyController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Title: HelloServerHandler
 * Description:  服务端业务逻辑
 * Version:1.0.0
 *
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    /*
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        System.out.println("服务端给客户端发送测试数据"+NettyController.newServerStr);
        ctx.writeAndFlush("服务端给客户端发送数据："+ NettyController.newServerStr+" \n");


      //  super.channelActive(ctx);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead");
       // super.channelRead(ctx, msg);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("服务端接受的消息 : " + s);
    }
    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       // ctx.flush();
        System.out.println("服务端信息接收完毕...");
    }
}