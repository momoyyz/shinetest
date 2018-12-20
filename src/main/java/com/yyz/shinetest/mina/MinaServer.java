package com.yyz.shinetest.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

@Service
public class MinaServer {
    // 提供服务端实现
    public static IoAcceptor accept = null;

    /**
     * 启动服务端
     *
     * @param port
     * @return
     * @throws IOException
     */
    public Boolean openServer(Integer port) throws IOException {
        accept = new NioSocketAcceptor();
        // 设置过滤器
        accept.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                        .forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));

        // 设置缓冲区大小
        accept.getSessionConfig().setReadBufferSize(1024);
        // 时间10s
        accept.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // 业务
        accept.setHandler(new ServerHandler());
        // 绑定端口并启动
        accept.bind(new InetSocketAddress(port));
        System.out.println("*****服务端开启成功 ->" + port + "*****");
        return true;
    }

    /**
     * 启动客户端
     *
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("openClient")
    public Boolean openClient(String host, Integer port) {
        // 提供客户端实现
        IoConnector connector = new NioSocketConnector();
        // 设置超时时间
        connector.setConnectTimeout(3000);
        // 设置过滤器(编码和解码)
        connector.getFilterChain().addLast("coderc", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));
        // 业务处理
        connector.setHandler(new ClientHandler());
        // 设置session属性,获取服务端连接
        ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
        // 等待我们的连接
        future.awaitUninterruptibly();
        System.out.println("*****客户端开启成功*****");
        return true;

    }

    /**
     * 获得客户端连接总数
     *
     * @return
     */
    public int getConNum() {
        return accept.getManagedSessionCount();
    }

    /**
     * 向每个客户端发送消息
     *
     * @return
     */
    public Boolean sendConMessage(String message) {

        IoSession session;
        Map conMap = accept.getManagedSessions();
        Iterator iter = conMap.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            session = (IoSession) conMap.get(key);
            session.write(message);
        }
        return true;
    }

    /**
     * 客户端返回的数据
     *
     * @return
     */
    public String returnMessage() {
        String returnMessage = "";
        IoSession session;
        Map conMap = accept.getManagedSessions();
        Iterator iter = conMap.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            session = (IoSession) conMap.get(key);
            returnMessage = (String) session.getAttribute("msg");
        }
        return returnMessage;
    }
}
