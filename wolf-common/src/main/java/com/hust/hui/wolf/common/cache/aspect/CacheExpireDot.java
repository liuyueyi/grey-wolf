package com.hust.hui.wolf.common.cache.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yihui on 2017/1/8.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheExpireDot {
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

}
