package com.yyz.shinetest.netty.server;

import com.yyz.shinetest.netty.util.MessageList;
import com.yyz.shinetest.netty.util.NettyConfig;
import com.yyz.shinetest.netty.util.NettySocketHolder;
import com.yyz.shinetest.web.MinaController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

/**
 * 接收类型不一样 会导致的操作走channelRead而不走channelRead0
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final static Logger log = Logger.getLogger(ServerHandler.class);

    /**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println();
        //保存用户channel
       int size= NettySocketHolder.getMAP().size();
        if(size==0){
            NettySocketHolder.put(1, (NioSocketChannel) ctx.channel());
        }else{
            NettySocketHolder.put(size+1, (NioSocketChannel) ctx.channel());

        }
    }

    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("netty 客户端与服务端连接关闭...");
        NettyConfig.group.remove(ctx.channel());
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        MessageList.add(s);
        //log.info("netty 服务到接收到的数据：" + s);
    }


}
