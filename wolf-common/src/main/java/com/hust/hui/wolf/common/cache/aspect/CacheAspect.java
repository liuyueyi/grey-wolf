package com.hust.hui.wolf.common.cache.aspect;


import com.hust.hui.wolf.common.cache.api.ICache;
import com.hust.hui.wolf.common.util.spel.SpelTool;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yihui on 16/9/25.
 */
@Aspect
@Component
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    private ICache redisCache;

    //    直接对注解进行切面
    @Around(value = "@annotation(cacheEnabledDot)")
    public Object execute(ProceedingJoinPoint point, CacheEnabledDot cacheEnabledDot) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Method method = getMethod(point);
            String cacheKey = getKey(cacheEnabledDot.prefix(), cacheEnabledDot.key(), cacheEnabledDot.skey(),
                    method, point.getArgs());

            Object result;
            Class<?> clz = method.getReturnType();
            if (clz == List.class) {
                result = redisCache.getArray(cacheKey, StringUtils.isBlank(cacheEnabledDot.clzName()) ? Object.class : Class.forName(cacheEnabledDot.clzName()));
            } else if (clz == String.class) {
                result = redisCache.getStr(cacheKey);
            } else {
                // 如果cacheDot 定义了clz, 则优先使用定义的clz; 否则使用方法签名的返回类型
                result = redisCache.getObject(cacheKey, StringUtils.isBlank(cacheEnabledDot.clzName()) ? clz : Class.forName(cacheEnabledDot.clzName()));
            }

            if (result != null) {
                return result;
            }

            // 缓存没有命中,则执行后,将结果塞入缓存
            result = point.proceed();
            redisCache.putObject(cacheKey, result, cacheEnabledDot.expire());
            return result;
        } catch (Exception e) {
            logger.error("exception! e: {}", e);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            logger.info("leave cache function! cost: {}", end - start);
        }
    }


    @After(value = "@annotation(cacheExpire)")
    public void expire(JoinPoint joinPoint, CacheExpireDot cacheExpire) {
        Method method = getMethod(joinPoint);
        String cacheKey = getKey(cacheExpire.prefix(), cacheExpire.key(), cacheExpire.skey(),
                method, joinPoint.getArgs());
        try {
            redisCache.expire(cacheKey);
        } catch (Exception e) {
            logger.error("expire key error! cacheKey: {}, method: {}", cacheKey, method.getName());
        }
    }


    /**
     * 获取被拦截方法对象
     * <p/>
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     * 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(JoinPoint pjp) {
//        不直接用这个方法是因为获取的是顶层接口或者父类的方法对象
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();


//        //获取参数的类型
//        Object[] args = pjp.getArgs();
//        Class[] argTypes = new Class[pjp.getArgs().length];
//        for (int i = 0; i < args.length; i++) {
//            argTypes[i] = args[i].getClass();
//        }
//        Method method = null;
//        try {
//            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//        return method;

    }

    private String getKey(String prefix, String key1, String key2, Method method, Object[] args) {

        StringBuilder builder = new StringBuilder();

        builder.append(prefix).append(":");

        if (StringUtils.isBlank(key2)) {
            return builder.append(SpelTool.parseKey(key1, method, args)).toString();
        }


        Map<String, String> map = SpelTool.parseKeys(Arrays.asList(key1, key2), method, args);
        if (map == null) {
            return null;
        }

        // 如果标识key为0, 则直接放弃,认为这种为无意义的标识
        String firstValue = map.get(key1);
        if ("0".equals(firstValue)) {
            return null;
        }

        builder.append(firstValue);
        if (map.get(key2) != null) {
            builder.append("_").append(map.get(key2));
        }


        return builder.toString();
    }
}
