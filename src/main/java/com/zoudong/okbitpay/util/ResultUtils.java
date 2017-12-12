package com.zoudong.okbitpay.util;

import com.zoudong.okbitpay.util.result.BaseResult;
import com.zoudong.okbitpay.util.result.Result;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ResultUtils {
    private static Logger logger = Logger.getLogger(ResultUtils.class);

    public static Result fillParameterfail(BindingResult bindingResult) {

        Result jsonResult = new Result();
        try {
            jsonResult.setStatus(BaseResult.fail);
            StringBuilder errMessage = new StringBuilder();
            if (bindingResult.hasErrors()) {
                logger.info(bindingResult.getAllErrors());
                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    errMessage.append(objectError.getDefaultMessage());
                }
                jsonResult.setMsg(errMessage.toString());
            } else {
                jsonResult.setMsg(BaseResult.fail);
            }
            jsonResult.setData(null);

        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result();
            result.setStatus(BaseResult.fail);
            result.setMsg(BaseResult.parameterfail);
            result.setStatus(BaseResult.fail);
            return result;
        }
        return jsonResult;
    }


    public static Result fillErrorMsg(String message) {
        Result jsonResult = new Result();
        jsonResult.setMsg(message);
        jsonResult.setStatus(BaseResult.fail);
        jsonResult.setData(null);
        return jsonResult;
    }


    public static Result fillSuccessData(Object data) {
        Result jsonResult = new Result();
        jsonResult.setStatus(BaseResult.success);
        jsonResult.setMsg(BaseResult.success);
        if (data == null) {
            jsonResult.setData(null);
        } else {
            jsonResult.setData(data);
        }
        return jsonResult;
    }


}
