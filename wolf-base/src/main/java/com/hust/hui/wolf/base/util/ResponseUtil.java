package com.hust.hui.wolf.base.util;

import com.hust.hui.wolf.base.domain.model.result.ResponseWrapper;

/**
 * Created by yihui on 16/9/25.
 */
public class ResponseUtil {
    private ResponseUtil() {}

    public static ResponseWrapper error(Integer code, String msg) {
        ResponseWrapper wrapper = new ResponseWrapper();
        wrapper.setStatus(new ResponseWrapper.Status(code, msg));
        return wrapper;
    }

    public static <T> ResponseWrapper<T> success(T result) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setStatus(new ResponseWrapper.Status(1001, "正常"));
        wrapper.setResult(result);
        return wrapper;
    }
}
