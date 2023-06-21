package io.cjl.spp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreManager {

    private static final String NAME = "bluetooth_info";
    public static final String BT_ADDRESS = "address";
    private static SharedPreManager instance = null;
    private SharedPreferences mPref = null;

    private SharedPreManager() {

    }

    public static SharedPreManager getInstance() {
        if (instance == null) {
            instance = new SharedPreManager();
        }
        return instance;
    }

    public void init(Context context) {
        mPref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public boolean getBoolValue(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public void setBoolValue(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public String getStringValue(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public void setStringValue(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public Long getLongValue(String key, Long defValue) {
        return mPref.getLong(key, defValue);
    }

    public void setLongValue(String key, Long value) {
        mPref.edit().putLong(key, value).apply();
    }

    public int getIntValue(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public void setIntValue(String key, int value) {
        mPref.edit().putInt(key, value).apply();
    }

    /**
     * 保存最后一次连接的蓝牙地址
     *
     * @param address 蓝牙地址
     */
    public void setLastAddress(String address) {
        setStringValue(BT_ADDRESS, address);
    }

    /**
     * 获取最后一次连接的蓝牙地址
     *
     * @return 地址
     */
    public String getLastAddress() {
        return getStringValue(BT_ADDRESS, null);
    }
}
