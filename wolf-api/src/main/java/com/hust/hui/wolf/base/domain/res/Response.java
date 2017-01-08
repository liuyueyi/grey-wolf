package com.hust.hui.wolf.base.domain.res;

/**
 * Created by yihui on 16/9/25.
 */
public class Response<T> {
    private Status status;
    private T result;

    public final static class Status {
        private Integer code;
        private String msg;

        public Status(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
