package com.yyz.shinetest.web.vo.req;


import com.yyz.shinetest.web.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqShutdownVMVO extends BaseVO {
    /**
     * 测试次数
     */
    private Integer testNum;
    /**
     * 报文
     */
    private String serverSendMessage;
    /**
     *测试内容
     */
    private String testContent;

}
