package com.hust.hui.wolf.console.anction.bench;

import com.hust.hui.wolf.console.anction.login.LoginRequest;
import com.hust.hui.wolf.console.aspect.EValidated;
import com.hust.hui.wolf.console.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试的类, 项目中无用
 *
 * Created by yihui on 16/10/21.
 */
@Component
public class AspectTest {

    private static final Logger logger = LoggerFactory.getLogger(AspectTest.class);

    @EValidated
    public void valMethodTest(String str, LoginRequest loginRequest) {
        logger.info("the str: {}", str);
        logger.info("the request: {}", loginRequest);
    }

    public void exceptionTest() throws ValidateException {
        throw new ValidateException();
    }

}
