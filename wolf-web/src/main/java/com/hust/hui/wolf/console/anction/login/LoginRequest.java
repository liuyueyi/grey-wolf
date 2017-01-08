package com.hust.hui.wolf.console.anction.login;

import com.hust.hui.wolf.console.anction.base.Request;
import com.hust.hui.wolf.console.aspect.ValidateDot;
import com.hust.hui.wolf.console.aspect.IValidate;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yihui on 16/10/20.
 */
@ValidateDot
@ToString
public class LoginRequest implements Request, IValidate {

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

    @Override
    public boolean isValid() {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        if (StringUtils.isNotBlank(username)) {
            return true;
        } else if (StringUtils.isNotBlank(email)) {
            return true;
        } else if (phone != null && phone > 0) {
            return true;
        } else {
            return false;
        }
    }
}
