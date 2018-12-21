package com.yyz.shinetest.web;

import com.yyz.shinetest.common.enums.RunStatusEnum;
import com.yyz.shinetest.service.ShineTestService;
import com.yyz.shinetest.web.vo.ResultResponse;

import com.yyz.shinetest.web.vo.req.ReqShutdownVMVO;
import com.yyz.shinetest.web.vo.resp.ResShutdownVMVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.log4j.Logger;

import java.io.IOException;

@RestController()
public class ShineTestController {

    private final static Logger log = Logger.getLogger(ShineTestController.class);

    @Autowired
    ShineTestService shineTestService;

    /**
     * 测试
     *
     * @return
     */
    @RequestMapping("testMethod")
    public ResultResponse<ResShutdownVMVO> testMethod(ReqShutdownVMVO reqShutdownVMVO) {
        log.info("开始测试功能->"+reqShutdownVMVO.getTestContent());
        ResultResponse<ResShutdownVMVO> resultResponse=new ResultResponse<>();

        try {
            resultResponse.setSuccessData(shineTestService.shutdownVM(reqShutdownVMVO));
        }catch (IOException e){
            resultResponse.setError(RunStatusEnum.TCP_ERROR);
            log.error(RunStatusEnum.TCP_ERROR.getMsg(),e);
        }catch (Exception e){
            ResShutdownVMVO resShutdownVMVO=new ResShutdownVMVO();
            resShutdownVMVO.setTestContent(reqShutdownVMVO.getTestContent());
            resShutdownVMVO.setServerSendMessage(reqShutdownVMVO.getServerSendMessage());
            resultResponse.setError(resShutdownVMVO);
            log.error(RunStatusEnum.TEXT_ERROR.getMsg(),e);
        }
        return resultResponse;
    }
    /**
     * 开启服务端
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("openServer")
    public ResultResponse<String> openServer(String host,Integer port) {
        log.info("准备开启服务端 ->"+port);
        ResultResponse<String> resultResponse=new ResultResponse<>();
        try {
            shineTestService.openServer(port);
            resultResponse.setSuccessData((RunStatusEnum.SERVER_SUCCESS.getMsg()));
            log.info("启动成功->"+port);
            System.out.println();
        }catch (Exception e){
            resultResponse.setError(RunStatusEnum.SERVER_ERROR);
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
    @RequestMapping("openClient")
    public ResultResponse<String> openClient(String host,Integer port) {
        log.info("准备开启客户端 ->"+host+":"+port);
        ResultResponse<String> resultResponse=new ResultResponse<>();
        try {
            shineTestService.opentClient(host,port);
            resultResponse.setSuccessData(RunStatusEnum.CLIENT_SUCCESS.getMsg());
            log.info(RunStatusEnum.CLIENT_SUCCESS.getMsg());
        }catch (Exception e){
            log.error(RunStatusEnum.CLIENT_ERROR,e);
            resultResponse.setError((RunStatusEnum.CLIENT_ERROR));
        }
        return resultResponse;
    }



}
