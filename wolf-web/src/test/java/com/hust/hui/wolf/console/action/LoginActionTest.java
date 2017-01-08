package com.hust.hui.wolf.console.action;

import com.hust.hui.wolf.console.anction.ResponseWrapper;
import com.hust.hui.wolf.console.anction.login.LoginAction;
import com.hust.hui.wolf.console.anction.login.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by yihui on 16/10/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:service.xml"})
public class LoginActionTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginActionTest.class);

    // 模拟request,response
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    // 注入loginController
    @Autowired
    private LoginAction loginController ;

    // 执行测试方法之前初始化模拟request,response
    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }


    /**
     *
     * @Title：testLogin
     * @Description: 测试用户登录
     */
    @Test
    public void testLogin() {
        try {
            String username = "小灰灰";
            String password = "123456";
            String email = null;
            Long phone = null;
            request.setParameter("username", username == null ? "" : username);
            request.setParameter("password", password == null ? "" : password);
            request.setParameter("email", email == null ? "" : email);
            request.setParameter("phone", phone == null ? "" : phone + "");

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(username);
            loginRequest.setPassword(password);
            loginRequest.setEmail(email);
            loginRequest.setPhone(phone);

            ResponseWrapper responseWrapper = loginController.execution(request, loginRequest);
            logger.info("the response: {}", responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testLoginFailed() {
       /* // 登录用户名失败
        try {
            String username = "小灰灰2";
            String password = "123456";
            String email = null;
            Long phone = null;
            request.setParameter("username", username == null ? "" : username);
            request.setParameter("password", password == null ? "" : password);
            request.setParameter("email", email == null ? "" : email);
            request.setParameter("phone", phone == null ? "" : phone + "");

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(username);
            loginRequest.setPassword(password);
            loginRequest.setEmail(email);
            loginRequest.setPhone(phone);

            ResponseWrapper responseWrapper = loginController.execution(request, loginRequest);
            logger.info("the response: {}", responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        try { // 参数异常,直接切面校验失败
            LoginRequest loginRequest = new LoginRequest();
            ResponseWrapper responseWrapper = loginController.execution(null, loginRequest);
            logger.info("the response: {}", responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
