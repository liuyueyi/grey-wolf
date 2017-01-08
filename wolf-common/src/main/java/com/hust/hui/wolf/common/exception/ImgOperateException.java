package com.hust.hui.wolf.common.exception;

/**
 * Created by yihui on 16/11/1.
 */
public class ImgOperateException extends Exception {
    public ImgOperateException(String message) {
        super(message);
    }

    @Override
    public ImgOperateException fillInStackTrace() {
        return this;
    }
}
