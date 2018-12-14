package com.yyz.shinetest.common.enums;

public enum RunStatusEnum {
    SUCCESS("000000", "请求操作成功!", true),
    CLIENT_SUCCESS("000001", "客户端启动成功!", true),
    SERVER_SUCCESS("000002", "服务器启动作成功!", true),
    SERVER_ERROR("100001", "服务器启动失败!", false),
    CLIENT_ERROR("100002", "客户端起订失败!", false),
    TCP_ERROR("100003", "TCP操作失败!", false),
    TEXT_ERROR("100004", "测试失败!", false),

    ;

    //返回code
    private String code;

    //返回信息
    private String msg;

    //返回状态，表示该次请求是否成功
    private Boolean status;

    RunStatusEnum(String code, String msg, Boolean status) {
        this.setCode(code);
        this.setMsg(msg);
        this.setStatus(status);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
