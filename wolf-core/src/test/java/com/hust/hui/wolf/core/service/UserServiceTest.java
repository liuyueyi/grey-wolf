package com.hust.hui.wolf.core.service;

import com.hust.hui.wolf.base.domain.model.entity.User;
import com.hust.hui.wolf.base.domain.service.UserService;
import com.hust.hui.wolf.core.repository.UserRepository;
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
@ContextConfiguration({"classpath*:spring/*.xml"})
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserByUname() {
        String uname = "小灰灰";
        User user = userService.getUserByName(uname);
        logger.info("the `{}` user is: {}", uname, user);

        uname = "";
        user = userService.getUserByName(uname);
        logger.info("the `{}` user is: {}", uname, user);

        uname = "wwf or 1=1";
        user = userService.getUserByName(uname);
        logger.info("the `{}` user is: {}", user);
    }
}
