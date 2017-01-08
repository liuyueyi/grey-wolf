package com.hust.hui.wolf.console.anction.login;

import com.hust.hui.wolf.base.domain.model.entity.UserInfo;
import com.hust.hui.wolf.base.domain.req.UserReqDO;
import com.hust.hui.wolf.base.service.UserService;
import com.hust.hui.wolf.console.anction.ResponseWrapper;
import com.hust.hui.wolf.console.anction.base.Action;
import com.hust.hui.wolf.console.aspect.ValidateDot;
import com.hust.hui.wolf.console.constants.ResponseCode;
import com.hust.hui.wolf.console.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 * <p/>
 * Created by yihui on 16/10/20.
 */
@Controller
public class LoginAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

    @Autowired
    private UserService userService;

    @ValidateDot
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseWrapper execution(HttpServletRequest httpServletRequest, LoginRequest loginRequest) {
        UserInfo user = userService.login(buildReqDO(loginRequest));

        LoginResponse response = new LoginResponse();
        if (user != null) {
            response.setNickname(user.getNickname());
            response.setWelcome("login success! welcome to grey wolf!");
            return ResponseUtil.buildSuccResponse(response);
        } else {
            response.setWelcome("login failed");
            return ResponseUtil.buildFailResponse(response, ResponseCode.ERROR);
        }
    }


    private UserReqDO buildReqDO(LoginRequest loginRequest) {
        UserReqDO userReqDO = new UserReqDO();
        userReqDO.setUname(loginRequest.getUsername());
        userReqDO.setEmail(loginRequest.getEmail());
        userReqDO.setPassword(loginRequest.getPassword());
        userReqDO.setPhone(loginRequest.getPhone());
        return userReqDO;
    }
}
