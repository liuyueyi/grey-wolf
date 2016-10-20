package com.hust.hui.wolf.base.domain.service;

import com.hust.hui.wolf.base.domain.model.entity.User;

/**
 * Created by yihui on 16/9/25.
 */
public interface UserService {

    User getUserByName(String uname);

    User login(String uname, String email, Long phone, String password);
}
