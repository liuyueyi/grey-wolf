package com.hust.hui.wolf.base.service;

import com.hust.hui.wolf.base.domain.model.entity.UserInfo;
import com.hust.hui.wolf.base.domain.req.UserReqDO;

/**
 * Created by yihui on 16/9/25.
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     *
     * @param uname
     * @return
     */
    UserInfo getUserByName(String uname);


    /**
     * 登录, 并获取用户信息
     *
     * @param userReqDO
     * @return
     */
    UserInfo login(UserReqDO userReqDO);


    /**
     * 用户注册
     *
     * @param userReqDO
     * @return
     */
    UserInfo regist(UserReqDO userReqDO);
}
