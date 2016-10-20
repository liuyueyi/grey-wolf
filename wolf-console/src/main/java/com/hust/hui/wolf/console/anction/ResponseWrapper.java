package com.hust.hui.wolf.console.anction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by yihui on 16/10/20.
 */
@ToString
public class ResponseWrapper<T> implements Serializable {

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    private T result;

    public ResponseWrapper() {
    }

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
}
