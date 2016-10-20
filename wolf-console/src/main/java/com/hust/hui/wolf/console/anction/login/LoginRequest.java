package com.hust.hui.wolf.console.anction.login;

import com.hust.hui.wolf.console.anction.Request;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by yihui on 16/10/20.
 */
public class LoginRequest implements Request {

    public LoginRequest() {
    }

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    @NonNull
    private String password;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Long phone;
}
