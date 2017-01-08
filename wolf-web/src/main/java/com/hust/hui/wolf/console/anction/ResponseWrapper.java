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

    @ToString
    public final static class Status {
        @Getter
        @Setter
        private Integer code;

        @Getter
        @Setter
        private String msg;

        public Status() {
        }

        public Status(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
