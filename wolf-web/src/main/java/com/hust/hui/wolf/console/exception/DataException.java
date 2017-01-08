package com.hust.hui.wolf.console.exception;

/**
 * Created by yihui on 16/10/23.
 */
public abstract class DataException extends Exception implements Error {

    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
