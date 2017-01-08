package com.hust.hui.wolf.console.anction;

import com.hust.hui.wolf.console.constants.ResponseCode;
import com.hust.hui.wolf.console.exception.Error;
import com.hust.hui.wolf.console.util.ResponseUtil;
import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yihui on 16/10/21.
 */
@ControllerAdvice(basePackages = "com.hust.hui,wolf.console.action")
public class ActionExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseWrapper handleException(Exception e) {
        boolean tag = false;
        if (e instanceof UndeclaredThrowableException) {
            if (((UndeclaredThrowableException) e).getUndeclaredThrowable() instanceof Error) {
                tag = true;
            }
        } else if (e instanceof Error) {
            tag = true;
        }

        if (tag) {
            return ResponseUtil.buildExceptionResponse(e.getMessage(), ((Error) e).returnStatus());
        }

        return ResponseUtil.buildExceptionResponse(e.getMessage() == null ? "内部错误" : e.getMessage(),
                ResponseCode.INTER_ERROR.buildStatus());
    }

}
