package com.yyz.shinetest.web;

import com.yyz.shinetest.common.enums.RunStatusEnum;
import com.yyz.shinetest.netty.client.NettyClient;
import com.yyz.shinetest.netty.server.NettyServer;
import com.yyz.shinetest.web.vo.ResultResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class NettyController {
    private final static Logger log = Logger.getLogger(NettyController.class);

    @Autowired
    NettyClient nettyClient;
    @Autowired
    NettyServer nettyServer;
    /**
     * 开启服务端
     * @param port
     * @return
     */
    @GetMapping("nettyOpenServer")
    public ResultResponse<Boolean> openServer(Integer port) {
        log.info("netty 服务端准备开启->"+port);
        ResultResponse<Boolean> resultResponse=new ResultResponse<>();
        try {
            //入参检查
            if(!StringUtils.isEmpty(port)){
                resultResponse.setSuccessData( nettyServer.bind(port));
            }else{
                resultResponse.setError(RunStatusEnum.ARG_NULL_ERROR);
                log.error(RunStatusEnum.ARG_NULL_ERROR.getMsg());
            }
        }catch (Exception e){
            log.error(RunStatusEnum.SERVER_ERROR.getMsg(),e);
        }
        return resultResponse;

    }

    /**
     * 开启客户端
     * @param host
     * @param port
     * @return
     */
    @GetMapping("nettyOpenClient")
    public ResultResponse<Boolean> openClient(String host, Integer port) {
        log.info("netty 客户端准备开启->"+port);
        ResultResponse<Boolean> resultResponse=new ResultResponse<>();
        try {
            //入参检查
            if(!StringUtils.isEmpty(port)&&!StringUtils.isEmpty(host)){
                resultResponse.setSuccessData( nettyClient.start(host,port));
            }else{
                resultResponse.setError(RunStatusEnum.ARG_NULL_ERROR);
                log.error(RunStatusEnum.ARG_NULL_ERROR.getMsg());
            }

        }catch (Exception e){
            resultResponse.setError(RunStatusEnum.CLIENT_ERROR);
            log.error(RunStatusEnum.CLIENT_ERROR.getMsg(),e);
        }
        return resultResponse;

    }

    /**
     * netty 服务端发送消息
     * @param message
     * @return
     * @throws InterruptedException
     */
    @PostMapping("nettySendMessage")
    public  ResultResponse<Boolean> sendMessage(String message,String testContent) {
        log.info("netty 开始测试 ->"+testContent);
        ResultResponse<Boolean> resultResponse=new ResultResponse<>();
        try {
            //入参检查
            if(!StringUtils.isEmpty(message)&&!StringUtils.isEmpty(testContent)){
                resultResponse.setSuccessData(nettyServer.nettySendMessage(message));
            }else{
                resultResponse.setError(RunStatusEnum.ARG_NULL_ERROR);
                log.error(RunStatusEnum.ARG_NULL_ERROR.getMsg());
            }
        }catch (Exception e){
            resultResponse.setError(RunStatusEnum.SEND_MESSAGE_ERROR);
            log.error(RunStatusEnum.SEND_MESSAGE_ERROR.getMsg(),e);
        }
        return resultResponse;
    }

    /**
     * 应答消息
     * @return
     */
    @GetMapping("nettyReturnMessage")
    public  ResultResponse<String> returnMessage(){
        ResultResponse<String> resultResponse=new ResultResponse<>();
        try {
            String message=nettyServer.nettyReturnMessage();
            resultResponse.setSuccessData(message);
            log.info("netty 应答消息："+message);
        }catch (Exception e){
            resultResponse.setError(RunStatusEnum.RETURN_DATA_ERROR);
            log.error(RunStatusEnum.RETURN_DATA_ERROR.getMsg(),e);
        }
        return resultResponse;
    }

    /**
     * 获取客户数量
     * @return
     */
    @GetMapping("nettyGetClientNum")
    public  ResultResponse<Integer> getClientNum(){
        ResultResponse<Integer> resultResponse=new ResultResponse<>();
        try {
            resultResponse.setSuccessData(nettyServer.getnettyClientNum());
        }catch (Exception e){
            resultResponse.setError(RunStatusEnum.CLIENT_NUM_ERROR);
            log.error(RunStatusEnum.CLIENT_NUM_ERROR.getMsg(),e );
        }
        return resultResponse;
    }
}
