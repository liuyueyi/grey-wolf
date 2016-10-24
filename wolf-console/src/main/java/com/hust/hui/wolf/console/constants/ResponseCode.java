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
    ERROR {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(3003, "请求失败");
        }
    },
    NOT_LOGIN {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(4004, "未登录");
        }
    },
    PARAMETER_ERROR {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(5005, "参数错误");
        }
    },
    VALIDATE_ERROR {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(5006, "校验失败");
        }
    },
    INTER_ERROR {
        @Override
        public ResponseWrapper.Status buildStatus() {
            return new ResponseWrapper.Status(5500, "内部错误");
        }
    }
    ;

    ResponseCode() {
    }

    public abstract ResponseWrapper.Status buildStatus();
}
