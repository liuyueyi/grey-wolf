package com.hust.hui.wolf.base.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 请求日志切面, 讲方法的调用都打印出来
 *
 * Created by yihui on 16/10/31.
 */
@Aspect
@Component
public class LogAspect {

    @After("execution(* com.hust.hui.wolf.core.service.*.*(..))")
    public void log(JoinPoint joinPoint) {
        // 输出来源应用,来源ip,服务名,方法参数,返回结果,执行时间等参数
    }

}
