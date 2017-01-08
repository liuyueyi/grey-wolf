package com.hust.hui.wolf.core.repository;

import com.hust.hui.wolf.base.domain.model.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 16/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/sql.xml", "classpath:spring/wolf-core-test.xml"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserByUsername() {
        long userId = 1;
        UserInfo user = userRepository.getUserByUserId(userId);
        System.out.println(user);

        String username = "wolf";
        UserInfo user2 = userRepository.getUserByUserName(username);
        System.out.println(user2);


        username  = "小灰灰";
        UserInfo user3 = userRepository.getUserByUserName(username);
        System.out.println(user3);

    }
}
