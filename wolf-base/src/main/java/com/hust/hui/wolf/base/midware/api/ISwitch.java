package com.hust.hui.wolf.base.midware.api;

/**
 * 远程开关配置
 *
 * Created by yihui on 16/9/25.
 */
public interface ISwitch {

    boolean getBoolean(String key, boolean defaultValue);


    String getString(String key, String defaultStr);


    int getInt(String key, int defaultInt);


    long getLong(String key, long defaultLong);


    double getDouble(String key, double defaultDouble);

}
