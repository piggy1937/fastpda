package com.step.pda.app.util.storage;

/*
 *  项目名：  PDA
 *  包名：    com.step.pda.app.util.storage
 *  文件名:   ShareUtils
 *  创建者:   LGL
 *  创建时间:  2016/10/26 23:24
 *  描述：    SharedPreferences封装
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.step.pda.app.Pda;

public class PreferenceUtils {

    public static final String NAME = "config";

    //键 值
    public static void putString(Context mContext, String key, String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    //键 默认值
    public static String getString(Context mContext, String key, String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    //键 值
    public static void putInt(Context mContext, String key, int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    //键 默认值
    public static int getInt(Context mContext, String key, int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    //键 值
    public static void putBoolean(Context mContext, String key, boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    //键 默认值
    public static boolean getBoolean(Context mContext, String key, boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    //刪除 单个
    public static void deleShare(Context mContext, String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //刪除 全部
    public static void deleAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }



    public static void setAppFlag(String key, boolean flag) {
        Context context=   Pda.getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        Context context=   Pda.getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
