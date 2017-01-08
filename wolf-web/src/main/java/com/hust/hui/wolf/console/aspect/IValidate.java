package com.hust.hui.wolf.console.aspect;

import com.hust.hui.wolf.console.exception.ValidateException;

/**
 * Created by yihui on 16/10/20.
 */
public interface IValidate {

    boolean isValid() throws ValidateException;
}
