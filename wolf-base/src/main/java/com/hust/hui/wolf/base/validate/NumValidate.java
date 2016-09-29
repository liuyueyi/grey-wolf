package com.hust.hui.wolf.base.validate;

/**
 * Created by yihui on 16/9/25.
 */
public class NumValidate {

    public static boolean idValidate(Number num) {
        return num != null && num.doubleValue() > 0;
    }

}
