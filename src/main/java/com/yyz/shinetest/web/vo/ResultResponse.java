package com.yyz.shinetest.web.vo;

import com.yyz.shinetest.common.enums.RunStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResultResponse<T> implements Serializable {

    private String code;

    private String msg;

    private Boolean status;

    private T data;


    public ResultResponse () {
        this.setCode("999999");
        this.setMsg("未知的系统异常");
        this.setStatus(false);
    }

    public void setSuccessData(T t) {
        this.setCode(RunStatusEnum.SUCCESS.getCode());
        this.setMsg(RunStatusEnum.SUCCESS.getMsg());
        this.setStatus(RunStatusEnum.SUCCESS.getStatus());
        this.setData(t);
    }



    public void setError(T t) {
        this.setCode(RunStatusEnum.TEXT_ERROR.getCode());
        this.setMsg(RunStatusEnum.TEXT_ERROR.getMsg());
        this.setStatus(RunStatusEnum.TEXT_ERROR.getStatus());
        this.setData(t);
    }
    public void setError(String error) {
        this.setCode(RunStatusEnum.TEXT_ERROR.getCode());
        this.setMsg(error);
        this.setStatus(RunStatusEnum.TEXT_ERROR.getStatus());
    }

    public void setError(RunStatusEnum error) {
        this.setCode(error.getCode());
        this.setMsg(error.getMsg());
        this.setStatus(error.getStatus());
    }
}
