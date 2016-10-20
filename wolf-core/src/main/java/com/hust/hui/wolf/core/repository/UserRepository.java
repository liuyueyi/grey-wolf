package com.hust.hui.wolf.core.repository;

import com.hust.hui.wolf.base.domain.model.entity.User;
import com.hust.hui.wolf.core.repository.dal.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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

    public User login(String uname, String email, Long phone, String password) {
        if (StringUtils.isBlank(password)) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("password", password);
        if (StringUtils.isNoneBlank(uname)) {
            map.put("username", uname);
        } else if (StringUtils.isNotBlank(email)) {
            map.put("email", email);
        } else if (phone != null && phone > 0) {
            map.put("phone", phone);
        } else {
            return null;
        }

        User user = userDao.login(map);
        return user;
    }

}
