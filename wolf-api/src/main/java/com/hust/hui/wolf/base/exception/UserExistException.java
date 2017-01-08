package com.hust.hui.wolf.base.exception;

/**
 * Created by yihui on 2017/1/8.
 */
public class UserExistException extends RuntimeException {

    public UserExistException(String message) {
        super(message);
    }

    @Override
    public UserExistException fillInStackTrace() {
        return this;
    }
}
