package com.hust.hui.wolf.base.midware.service.cache;

import com.alibaba.fastjson.JSON;
import com.hust.hui.wolf.base.exception.CacheException;
import com.hust.hui.wolf.base.midware.api.ICache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yihui on 16/9/25.
 */
public class RedisCache implements ICache, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private JedisPool masterJedis;

    private JedisPool slaveJedis;

    private String masterConf;

    private String slaveConf;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.initialPool();
    }

    private String[] splitConf(String conf) {
        if (!StringUtils.contains(conf, ":")) {
            return null;
        }

        String[] pair = StringUtils.split(conf, ":");
        if (pair == null || pair.length != 2) {
            return null;
        } else {
            return pair;
        }
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() throws CacheException {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(300);
        config.setMaxIdle(10);
        config.setMinIdle(3);
        config.setMaxWaitMillis(300L);
        config.setTestOnBorrow(false);


        String[] master = splitConf(masterConf);
        String[] slave = splitConf(slaveConf);
        if (master == null) {
            throw new CacheException("master conf is null");
        }

        masterJedis = new JedisPool(config, master[0], Integer.parseInt(master[1]));

        if (slave == null) {
            slaveJedis = masterJedis;
        } else {
            slaveJedis = new JedisPool(config, slave[0], Integer.parseInt(slave[1]));
        }
    }


    public void setMasterConf(String masterConf) {
        this.masterConf = masterConf;
    }

    public String getMasterConf() {
        return masterConf;
    }

    public String getSlaveConf() {
        return slaveConf;
    }

    public void setSlaveConf(String slaveConf) {
        this.slaveConf = slaveConf;
    }


    @Override
    public boolean putObject(String key, Object obj, int expire) {
        if (null == key || null == obj) {
            throw new IllegalArgumentException("prefix or obj is null" + key + " obj: " + obj);
        }
        Jedis jedis = null;
        try {
            jedis = masterJedis.getResource();
            String value = JSON.toJSONString(obj);
            String ans = jedis.setex(key, expire, value);
            return true;
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            if (jedis != null) {
                masterJedis.returnResource(jedis);
            }
        }
        return false;
    }

    @Override
    public String getStr(String key) {
        if (null == key) {
            throw new IllegalArgumentException("prefix is null");
        }
        Jedis jedis = null;
        try {
            jedis = slaveJedis.getResource();
            String str = jedis.get(key);
            return str;
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            if (jedis != null) {
                slaveJedis.returnResource(jedis);
            }
        }
        return null;
    }

    @Override
    public <T> Object getObject(String key, Class<T> clz) {
        String value = getStr(key);
        return StringUtils.isBlank(value) ? value : JSON.parseObject(value, clz);
    }

    @Override
    public boolean expire(String key) {
        if (null == key) {
            throw new IllegalArgumentException("prefix is null");
        }
        Jedis jedis = null;
        try {
            jedis = masterJedis.getResource();
            return jedis.del(key) >= 0;
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            if (jedis != null) {
                masterJedis.returnResource(jedis);
            }
        }
        return false;
    }

}
