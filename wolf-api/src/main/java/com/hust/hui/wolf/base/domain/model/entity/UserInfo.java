package com.hust.hui.wolf.base.domain.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * data 注解提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 *
 * Created by yihui on 16/9/25.
 */
@Data
@NoArgsConstructor
public class UserInfo {

    private String username;

    private Long userId;

    private String nickname;

    private String password;

    private String img;

    private String email;

    private Long phone;

    private String address;

    private String extra;

    private Integer isDeleted;

    private Integer created;

    private Integer updated;

}
