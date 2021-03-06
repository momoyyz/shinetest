package com.yyz.shinetest.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter{

    private final static Logger log = Logger.getLogger(ServerHandler.class);

    /**
     * 异常
     * @param session
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.error("mina 服务端连接出现异常");
    }

    /**
     * 服务端接收数据
     * @param session
     * @param   message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String msg = (String)message;
        session.setAttribute("msg",msg);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.info("mina 服务端发送数据");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("mina 服务端session关闭");
    }

//    @Override
//    public void sessionCreated(IoSession session) throws Exception {
//        System.out.println("mina 服务端创建Session");
//    }
//
//    @Override
//    public void sessionIdle(IoSession session, IdleStatus status)
//            throws Exception {
//        System.out.println("mina 服务端空闲状态");
//    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("mina 服务端打开Session用于读写数据");
    }

}
