package com.hust.hui.wolf.console.aspect;

import com.hust.hui.wolf.console.anction.bench.AspectTest;
import com.hust.hui.wolf.console.anction.login.LoginRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 16/10/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:service.xml"})
public class ValidateAspectTest {
    private static final Logger logger = LoggerFactory.getLogger(ValidateAspectTest.class);

    @Autowired
    private AspectTest aspectTest;

    @Test
    public void testValidateAspect() {
        String str = "hello world!";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("123456");
        loginRequest.setUsername("小灰灰");

        aspectTest.valMethodTest(str, loginRequest);
    }

    /**
     * todo 单测的切面为什么切不进来 ?
     * @param str
     * @param loginRequest
     */
    @EValidated
    public void testValidateAspect2(String str, LoginRequest loginRequest) {
        logger.info("the str: {}", str);
        logger.info("the request: {}", loginRequest);
    }



    /**
     * 判断一个类A是否为另一个类的子类B
     *
     * B.class.isAssignableFrom(A.class)
     */
    @Test
    public void testSubClass() {
        LoginRequest loginRequest = new LoginRequest();
        if (IValidate.class.isAssignableFrom(loginRequest.getClass())) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }

        String str = "hello world2!";
        loginRequest.setPassword("123456");
        loginRequest.setUsername("小灰灰");
        this.testValidateAspect2(str, loginRequest);
    }

}
