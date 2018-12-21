package com.yyz.shinetest.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.util.Random;


public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final static Logger log = Logger.getLogger(ClientHandler.class);

    /**
     *接收数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("netty 客户端接收到数据：" + msg);
        Random random=new Random();
         ctx.writeAndFlush("应答消息"+random.nextInt(100));

    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("netty 客户端异常");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }

}
