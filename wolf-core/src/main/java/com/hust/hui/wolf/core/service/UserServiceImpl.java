package com.hust.hui.wolf.core.service;

import com.hust.hui.wolf.base.domain.model.entity.UserInfo;
import com.hust.hui.wolf.base.domain.req.UserReqDO;
import com.hust.hui.wolf.base.exception.UserExistException;
import com.hust.hui.wolf.base.service.UserService;
import com.hust.hui.wolf.common.validate.NumValidate;
import com.hust.hui.wolf.core.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yihui on 16/9/25.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_AVATAR = "https://avatars0.githubusercontent.com/u/5125892?v=3&s=466";

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserInfo getUserByName(String uname) {
        if (StringUtils.isBlank(uname)) {
            return null;
        }

        return userRepository.getUserByUserName(uname);
    }


    /**
     * 登录并获取用户信息
     * @param userReqDO
     * @return
     */
    public UserInfo login(UserReqDO userReqDO) {
        if (StringUtils.isBlank(userReqDO.getPassword()) ||
                (StringUtils.isBlank(userReqDO.getUname()) &&
                        StringUtils.isBlank(userReqDO.getEmail()) &&
                        (userReqDO.getPhone() == null || userReqDO.getPhone() < 0))) {
            return null;
        }

        return userRepository.login(userReqDO.getUname(), userReqDO.getEmail(), userReqDO.getPhone(), userReqDO.getPassword());
    }


    /**
     * 注册
     * @param userReqDO
     * @return
     */
    public UserInfo regist(UserReqDO userReqDO) {
        UserInfo userInfo = new UserInfo();

        if (StringUtils.isBlank(userReqDO.getUname())) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        userInfo.setUsername(userReqDO.getUname());


        if (NumValidate.numValidate(userReqDO.getPhone())) {
            throw new IllegalArgumentException("电话号码不能为空");
        }
        userInfo.setPhone(userReqDO.getPhone());

        if (StringUtils.isBlank(userReqDO.getEmail())) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
        userInfo.setEmail(userReqDO.getEmail());

        if (StringUtils.isBlank(userReqDO.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }
        userInfo.setPassword(userReqDO.getPassword());


        if (StringUtils.isBlank(userReqDO.getNickName())) {
            userInfo.setNickname(userReqDO.getNickName());
        } else {
            userInfo.setNickname(userReqDO.getNickName());
        }


        if (StringUtils.isBlank(userReqDO.getAddress())) {
            userInfo.setAddress("");
        } else {
            userInfo.setAddress(userReqDO.getAddress());
        }

        if (StringUtils.isBlank(userReqDO.getAvatar())) {
            userInfo.setImg(DEFAULT_AVATAR);
        }

        userInfo.setIsDeleted(0);
        userInfo.setExtra("");

        // 首先判断用户是否存在
        UserInfo user = userRepository.getUserByUserName(userReqDO.getUname());
        if (user != null) {
            throw new UserExistException("用户名已存在!");
        }

        userInfo = userRepository.addUser(userInfo);
        if (userInfo == null) {
            // todo 注册失败报警
            logger.error("register error!");
        }

        return userInfo;
    }

}
