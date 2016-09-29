package com.hust.hui.wolf.core.repository;

import com.hust.hui.wolf.base.domain.model.entity.User;
import com.hust.hui.wolf.core.repository.dal.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by yihui on 16/9/25.
 */
@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;


    public User getUserByUserName(String username) {
        return userDao.getUserByUname(username);
    }


    public User getUserByUserId(long userId) {
        return userDao.getUserByUid(userId);
    }

}
