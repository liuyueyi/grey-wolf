package com.hust.hui.wolf.core.midware;

import com.hust.hui.wolf.common.cache.api.ICache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 16/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/sql.xml", "classpath:spring/wolf-core-test.xml"})
public class CacheTest {

    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

    @Autowired
    private ICache redisCache;


    @Test
    public void testCache() {
        String key = "wolf_test_key";
        String value = "hello worlf";

        boolean ans = redisCache.putObject(key, value, 1000);
        logger.info("the ans : {}", ans);

        String str = redisCache.getStr(key);
        logger.info("the cache obj: {}", str);

        ans = redisCache.expire(key);
        logger.info("expire prefix! {}", ans);


        String str2 = (String) redisCache.getObject(key, String.class);
        logger.info("the str is : {}", str2);

    }

}
