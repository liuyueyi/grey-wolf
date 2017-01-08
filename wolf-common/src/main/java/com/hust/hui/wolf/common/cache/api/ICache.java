package com.hust.hui.wolf.common.cache.api;

import java.util.List;

/**
 * Created by yihui on 16/9/25.
 */
public interface ICache {

    /**
     * 将对象塞入缓存
     * @param key
     * @param obj
     * @param expire
     * @return
     */
    boolean putObject(String key, Object obj, int expire);


    /**
     * 从缓存中获取对象
     * @param key
     * @param clz
     * @param <T>
     * @return
     */
    <T> Object getObject(String key, Class<T> clz);


    <T> List<T> getArray(String key, Class<T> clz);


    /**
     * 从缓存中获取字符串
     * @param key
     * @return
     */
    String getStr(String key);


    /**
     * 失效缓存
     * @param key
     * @return
     */
    boolean expire(String... key);

}
