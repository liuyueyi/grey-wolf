package com.hust.hui.wolf.core.service;

import com.hust.hui.wolf.base.annotation.CacheEnabled;
import com.hust.hui.wolf.base.domain.model.entity.User;
import com.hust.hui.wolf.base.domain.service.UserService;
import com.hust.hui.wolf.core.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yihui on 16/9/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @CacheEnabled(prefix = "wolf_uname_prefix_", fieldKey = "#uname", expire = 1000)
    public User getUserByName(String uname) {
        if (StringUtils.isBlank(uname)) {
            return null;
        }

        return userRepository.getUserByUserName(uname);
    }


    public User login(String uname, String email, Long phone, String password) {
        if (StringUtils.isBlank(password) ||
                (StringUtils.isBlank(uname) &&
                        StringUtils.isBlank(email) &&
                        (phone == null || phone < 0))) {
            return null;
        }

        return userRepository.login(uname, email, phone, password);
    }
}
