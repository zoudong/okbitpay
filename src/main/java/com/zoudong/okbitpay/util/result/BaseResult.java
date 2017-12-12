package com.zoudong.okbitpay.util.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult implements Serializable {

    //状态码
    private String status;
    private String msg;
    private Object data;
    private Object externData;

    //执行成功
    public static final String success = "success";
    //执行失败
    public static final String fail = "fail";
    //处理中
    public static final String processing = "processing";
    //入参错误
    public static final String parameterfail = "parameterfail";
    //入参重复
    public static final String paramrepeat = "paramrepeat";
    //内部错误
    public static final String internalerror = "internalerror";
    //网络超时
    public static final String connectiontimeouts = "connectiontimeouts";



    public BaseResult() {
        this.status = fail;
        this.data = null;
        this.msg = fail;
        this.externData = null;
    }


}
