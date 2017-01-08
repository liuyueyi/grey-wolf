package com.hust.hui.wolf.common.test;

import com.hust.hui.wolf.common.util.num.NumUtil;
import org.junit.Test;

/**
 * Created by yihui on 2017/1/8.
 */
public class NumUtilTest {

    @Test
    public void parseNumTest() {
        double d = 12.3333;
        double res = NumUtil.formatNum(d, 2);
        System.out.println("result: " + res);
    }

}
