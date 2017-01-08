package com.hust.hui.wolf.base.domain.req;

import lombok.Data;

/**
 * Created by yihui on 2017/1/8.
 */
@Data
public class UserReqDO {

    private String uname;

    private String email;

    private Long phone;

    private String password;

    private String nickName;

    private String address;

    private String avatar;
}
