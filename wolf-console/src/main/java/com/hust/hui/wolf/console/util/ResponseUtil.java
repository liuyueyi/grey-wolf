package com.hust.hui.wolf.console.util;

import com.hust.hui.wolf.console.anction.ResponseWrapper;
import com.hust.hui.wolf.console.constants.ResponseCode;

/**
 * Created by yihui on 16/10/20.
 */
public class ResponseUtil {

    public static <T> ResponseWrapper<T> buildSuccResponse(T data) {
        return buildFailResponse(data, ResponseCode.SUCCESS);
    }


    public static <T> ResponseWrapper<T> buildFailResponse(T data, ResponseCode responseCode) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        ResponseWrapper.Status status = responseCode.buildStatus();
        responseWrapper.setStatus(status);
        responseWrapper.setResult(data);
        return responseWrapper;
    }

    public static <T> ResponseWrapper<T> buildExceptionResponse(T data, ResponseWrapper.Status status) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setStatus(status);
        responseWrapper.setResult(data);
        return responseWrapper;
    }
}
