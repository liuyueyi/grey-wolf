package com.hust.hui.wolf.base.util;

import java.math.BigDecimal;

/**
 * Created by yihui on 16/9/25.
 */
public class NumUtil {

    /**
     * 保留n位精度
     * <p/>
     * DecimalFormat df = new DecimalFormat("#.00");
     * df.format(num);
     *
     * @param num
     * @param precesion
     * @return
     */
    public static double formatNum(double num, int precesion) {
        BigDecimal bg = new BigDecimal(num);
        return bg.setScale(precesion, BigDecimal.ROUND_HALF_UP).doubleValue();


    }
}
