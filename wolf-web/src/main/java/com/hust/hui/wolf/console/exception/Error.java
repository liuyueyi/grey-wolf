package com.hust.hui.wolf.console.exception;

import com.hust.hui.wolf.console.anction.ResponseWrapper;

/**
 * Created by yihui on 16/10/21.
 */
public interface Error {

    ResponseWrapper.Status returnStatus();

}
