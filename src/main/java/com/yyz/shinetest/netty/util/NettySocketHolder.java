package com.yyz.shinetest.netty.util;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 22/05/2018 18:33
 * @since JDK 1.8
 */
public class NettySocketHolder {
    private static  Map<Integer, NioSocketChannel> MAP = new ConcurrentHashMap<>(16);

    public static void put(Integer id, NioSocketChannel socketChannel) {
        MAP.put(id, socketChannel);
    }

    public static NioSocketChannel get(Integer id) {
        return MAP.get(id);
    }

    public static Map<Integer, NioSocketChannel> getMAP() {
        return MAP;
    }

    public static void remove(NioSocketChannel nioSocketChannel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> MAP.remove(entry.getKey()));
    }
}
