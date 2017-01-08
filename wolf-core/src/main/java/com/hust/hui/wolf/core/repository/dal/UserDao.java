package com.hust.hui.wolf.core.repository.dal;

import com.hust.hui.wolf.base.domain.model.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by yihui on 16/9/25.
 */
public interface UserDao {

    boolean addUser(UserInfo userInfo);

    UserInfo getUserByUname(@Param("username") String uname);

    UserInfo getUserByUid(@Param("userId") long userId);

    UserInfo login(Map<String, Object> params);
}
