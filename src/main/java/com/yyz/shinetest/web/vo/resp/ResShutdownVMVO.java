package com.yyz.shinetest.web.vo.resp;


import com.yyz.shinetest.web.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResShutdownVMVO extends BaseVO {
    private Integer testNum;

    private String serverSendMessage;

    private String clientReturnMessage;

    private String testContent;
}
