package com.hust.hui.wolf.core.repository;

import com.hust.hui.wolf.base.domain.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 16/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/*.xml"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserByUsername() {
        long userId = 1;
        User user = userRepository.getUserByUserId(userId);
        System.out.println(user);

        String username = "wolf";
        User user2 = userRepository.getUserByUserName(username);
        System.out.println(user2);


        username  = "小灰灰";
        User user3 = userRepository.getUserByUserName(username);
        System.out.println(user3);

    }
}
