package com.hust.hui.wolf.console.exception;

import com.hust.hui.wolf.console.anction.ResponseWrapper;
import com.hust.hui.wolf.console.constants.ResponseCode;

/**
 * Created by yihui on 16/10/21.
 */
public class ValidateException extends DataException {

    public ValidateException() {
        super();
    }

    @Override
    public ResponseWrapper.Status returnStatus() {
        return ResponseCode.VALIDATE_ERROR.buildStatus();
    }
}
