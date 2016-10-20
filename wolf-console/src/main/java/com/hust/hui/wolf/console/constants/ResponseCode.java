package com.hust.hui.wolf.console.constants;

import com.hust.hui.wolf.console.anction.ResponseWrapper;

/**
 * Created by yihui on 16/10/20.
 */
public enum ResponseCode {
    SUCCESS {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(200, "SUCCESS");
        }
    },
    NOT_LOGIN {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(4004, "NOT_LOGIN");
        }
    },
    PARAMETER_ERROR {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(5005, "PARAMETER_ERROR");
        }
    };

    ResponseCode() {
    }

    public abstract ResponseWrapper.Status buildStatus();
}
