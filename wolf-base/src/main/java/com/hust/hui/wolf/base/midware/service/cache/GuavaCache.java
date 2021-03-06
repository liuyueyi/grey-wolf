package com.hust.hui.wolf.base.midware.service.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hust.hui.wolf.base.midware.api.ICache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * Created by yihui on 16/9/25.
 */
public class GuavaCache implements ICache, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCache.class);

    private Cache<String, String> cache = null;

    private Long expireMin = 3L;

    private Long maxmumSize = 1000L;


    public String getStr(String key) {
        if (null == key) {
            throw new IllegalArgumentException("prefix is null");
        }

        try {
            return cache.getIfPresent(key);
        } catch (Exception e) {
            LOGGER.error("get guava object failed, {}", e);
            return null;
        }
    }

    public <T> T getObject(String key, Class<T> clz) {
        String str = getStr(key);
        return JSON.parseObject(str, clz);
    }


    public boolean putObject(String key, Object value, int expire) {
        if (null == key || null == value) {
            throw new IllegalArgumentException("prefix or value is null");
        }
        try {
            String valStr = JSON.toJSONString(value);
            cache.put(key, valStr);
            return true;
        } catch (Exception e) {
            LOGGER.error("set guava object failed, {}", e);
            return false;
        }
    }

    public boolean expire(String key) {
        if (null == key) {
            throw new IllegalArgumentException("prefix is null");
        }
        try {
            cache.invalidate(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("expire guava object failed, {}", e);
            return false;
        }
    }

    public void init() throws Exception {
        cache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(expireMin, TimeUnit.MINUTES)
                .maximumSize(maxmumSize)
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
