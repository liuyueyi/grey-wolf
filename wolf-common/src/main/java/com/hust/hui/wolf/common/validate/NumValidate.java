package com.hust.hui.wolf.common.validate;

/**
 * Created by yihui on 16/9/25.
 */
public class NumValidate {

    public static boolean numValidate(Number num) {
        return num != null && num.doubleValue() > 0;
    }

}
