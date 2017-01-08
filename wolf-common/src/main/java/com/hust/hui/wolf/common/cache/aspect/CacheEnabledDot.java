package com.hust.hui.wolf.common.cache.aspect;

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
public @interface CacheEnabledDot {

    /**
     * 缓存时间
     * @return
     */
    int expire() default 60;

    /**
     * 缓存前缀
     * @return
     */
    String prefix() default "";

    /**
     * 主标识
     * @return
     */
    String key() default "";

    /**
     * 附标识
     * @return
     */
    String skey() default "";

    /**
     * 缓存对象的类型
     * @return
     */
    String clzName() default "";
}
