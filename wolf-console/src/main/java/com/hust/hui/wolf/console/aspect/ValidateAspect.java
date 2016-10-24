package com.hust.hui.wolf.console.aspect;

import com.hust.hui.wolf.console.exception.ValidateException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by yihui on 16/10/21.
 */
@Aspect
@Component
public class ValidateAspect {

    @Before("@annotation(com.hust.hui.wolf.console.aspect.EValidated)")
    public void execute(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        for (Object arg : args) {
            if (arg != null
                    && arg.getClass().isAnnotationPresent(EValidated.class)
                    && IValidate.class.isAssignableFrom(arg.getClass())) {
                // 参数校验失败
                if (!((IValidate) arg).isValid()) {
                    throw new ValidateException();
                }
            }
        }
    }


    @Before("execution(* com.hust.hui.wolf.console..*(..))")
    public void before() {
        System.out.println("--------- aspect console before --------");
    }

}
