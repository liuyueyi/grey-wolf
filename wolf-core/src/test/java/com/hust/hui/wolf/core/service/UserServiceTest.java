package com.hust.hui.wolf.core.service;

import com.hust.hui.wolf.base.domain.model.entity.UserInfo;
import com.hust.hui.wolf.base.domain.req.UserReqDO;
import com.hust.hui.wolf.base.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 16/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/sql.xml", "classpath:spring/wolf-core-test.xml"})
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByUname() {
        String uname = "小灰灰";
        UserInfo user = userService.getUserByName(uname);
        logger.info("the `{}` user is: {}", uname, user);

        uname = "";
        user = userService.getUserByName(uname);
        logger.info("the `{}` user is: {}", uname, user);

        uname = "wwf or 1=1";
        user = userService.getUserByName(uname);
        logger.info("the `{}` user is: {}", user);
    }

    @Test
    public void testLogin() {
        String uname = "username";
        String password = "123456";
        String email = "greywolf@xxx.com";
        Long phone = 15971112301L;

        UserReqDO userReqDO = new UserReqDO();
        userReqDO.setUname(uname);
        userReqDO.setPassword(password);

        UserInfo ans = userService.login(userReqDO);
        logger.info("login by illegal uname : {}", ans);

        userReqDO.setUname(null);
        userReqDO.setEmail(email);
        ans = userService.login(userReqDO);
        logger.info("login by email : {}", ans);

        userReqDO.setEmail(null);
        userReqDO.setPhone(phone);
        ans = userService.login(userReqDO);
        logger.info("login by phone : {}", ans);


        userReqDO.setEmail(null);
        userReqDO.setUname("小灰灰");
        ans = userService.login(userReqDO);
        logger.info("login by uname : {}", ans);
    }
}
