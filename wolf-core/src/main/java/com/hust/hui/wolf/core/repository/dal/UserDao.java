package com.hust.hui.wolf.core.repository.dal;

import com.hust.hui.wolf.base.domain.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by yihui on 16/9/25.
 */
public interface UserDao {

    User getUserByUname(@Param("username") String uname);

    User getUserByUid(@Param("userId") long userId);

    User login(Map<String, Object> params);
}
