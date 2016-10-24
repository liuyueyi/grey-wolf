package com.hust.hui.wolf.console.anction.login;

import com.hust.hui.wolf.console.anction.base.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yihui on 16/10/20.
 */
@ToString
public class LoginResponse implements Response {

    @Getter
    @Setter
    private String nickname;


    @Getter
    @Setter
    private String welcome;

}
