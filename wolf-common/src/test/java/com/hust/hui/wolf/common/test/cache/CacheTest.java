package com.hust.hui.wolf.common.test.cache;

import com.hust.hui.wolf.common.cache.api.ICache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yihui on 2017/1/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:common-test.xml"})
public class CacheTest {

    @Resource(name = "redisCache")
    private ICache redisCache;

    @Test
    public void testCache() {
        String key = "testKey";
        String value = "value";

        redisCache.putObject(key, value, 100);

        String obj = redisCache.getStr(key);
        System.out.println("the cache rsult: {}" + obj);
    }


}
