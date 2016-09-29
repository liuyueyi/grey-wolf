package com.hust.hui.wolf.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * <p/>
 * Created by yihui on 16/9/25.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEnabled {

    int expire() default 60;

    String prefix() default "";

    String suffix() default "";

    String fieldKey() default "";

}
