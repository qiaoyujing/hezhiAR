package com.kezhi.android.kezhiar.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by songtao on 2017/5/7.
 */

public class SharedPreferencesHelper {
    private static final String FILE_NAME = "app_data";
    private static SharedPreferences mSharedPreferences;//单例
    private static SharedPreferencesHelper instance;//单例

    private SharedPreferencesHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    }

    //初始化单例
    public static synchronized void init(Context context){
        if (instance == null){
            instance = new SharedPreferencesHelper(context);
        }
    }

    //获取单例
    public static SharedPreferencesHelper getInstance(){
        if (instance == null){
            throw new RuntimeException("class should init!");
        }
        return instance;
    }

    //保存数据
    public void saveData(String key , Object data){
        String type = data.getClass().getSimpleName();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if ("Integer".equals(type)){
            editor.putInt(key,(Integer)data);
        }else if ("Boolean".equals(type)){
            editor.putBoolean(key,(Boolean)data);
        }else if ("String".equals(type)){
            editor.putString(key,(String)data);
        }else if ("Float".equals(type)){
            editor.putFloat(key,(Float)data);
        }else if ("Long".equals(type)){
            editor.putLong(key,(Long)data);
        }
        editor.commit();
    }

    //获取数据
    public Object getData(String key, Object defaultValue){
        String type = defaultValue.getClass().getSimpleName();
        if ("Integer".equals(type)){
            return mSharedPreferences.getInt(key, (Integer) defaultValue);
        }else if ("Boolean".equals(type)){
            return mSharedPreferences.getBoolean(key, (Boolean) defaultValue);
        }else if ("String".equals(type)){
            return mSharedPreferences.getString(key, (String) defaultValue);
        }else if ("Float".equals(type)){
            return mSharedPreferences.getFloat(key, (Float) defaultValue);
        }else if ("Long".equals(type)){
            return mSharedPreferences.getLong(key, (Long) defaultValue);
        }
        return null;
    }
    public void clearData(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear().commit();
    }

}
