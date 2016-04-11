package com.addbean.autils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.security.GeneralSecurityException;
import java.util.Locale;
import com.google.gson.Gson;
/**
 * 2016年1月6日添加了对数据保存和读取加密操作
 */

public class Preferences {

    private final static String LANGUAGE_KEY = "language_key";

    private final static String LANGUAGE_KEY_VAL = "language_key_VAL";

    /**
     * 保存变量接口 此接口不加密
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveDataToSharedPreferences(Context context, String savingName, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(savingName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * @param context
     * @param key     键值
     * @return
     */
    public static String getDataFromSharedPreferences(Context context, String savingName, String key) {
        SharedPreferences settings = context.getSharedPreferences(savingName, Context.MODE_PRIVATE);
        String data = settings.getString(key, null);
        return data;
    }

    /**
     * 保存当前的语言
     *
     * @param context
     */
    public static void setCurrentLanguage(Context context, Locale locale) {
        SharedPreferences settings = context.getSharedPreferences("languages", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        if (locale == Locale.SIMPLIFIED_CHINESE) {
            editor.putString(LANGUAGE_KEY, "zh");
            editor.putInt(LANGUAGE_KEY_VAL, 1);
        } else if (locale == Locale.JAPANESE) {
            editor.putString(LANGUAGE_KEY, "ja");
            editor.putInt(LANGUAGE_KEY_VAL, 2);
        } else if (locale == Locale.ENGLISH) {
            editor.putString(LANGUAGE_KEY, "en");
            editor.putInt(LANGUAGE_KEY_VAL, 3);
        }
        editor.commit();
    }

    /**
     * 获取当前语言代码；
     *
     * @param context
     * @return
     */
    public static int getCurrentLanguageId(Context context) {
        SharedPreferences settings = context.getSharedPreferences("languages", Context.MODE_PRIVATE);
        return settings.getInt(LANGUAGE_KEY_VAL, 1);
    }

    /**
     * 获取当前价格
     *
     * @param context
     * @return
     */
    public static Locale getCurrentLanguage(Context context) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        SharedPreferences settings = context.getSharedPreferences("languages", Context.MODE_PRIVATE);
        String data = settings.getString(LANGUAGE_KEY, "1");
        if (!TextUtils.isEmpty(data)) {
            if (data.equals("zh")) {
                return Locale.SIMPLIFIED_CHINESE;
            } else if (data.equals("en")) {
                return Locale.ENGLISH;
            } else if (data.equals("ja")) {
                return Locale.JAPANESE;
            }
        }
        return locale;
    }

    public static int getCurrentLanguageCode(Context context) {
        Locale _locale = context.getResources().getConfiguration().locale;
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(_locale.getLanguage())) {
            return 1;
        } else if (Locale.JAPANESE.getLanguage().equals(_locale.getLanguage())) {
            return 2;
        } else if (Locale.ENGLISH.getLanguage().equals(_locale.getLanguage())) {
            return 3;
        }
        return 1;
    }

    /**
     * 保存T数据
     */
    public static <T> boolean saveObj(Context context, String savingName, T entity) {
        try {
            SharedPreferences settings = context.getSharedPreferences(savingName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            Gson gson =  new Gson();
            String objString = gson.toJson(entity);
            //绑定设备信息，动态生成密码
            String name= new DeviceUuidFactory(context).getDeviceUuid().toString();
            String encryptedMsg = AESCrypt.encrypt(name, objString);
            editor.putString(entity.getClass().getName(), encryptedMsg);
            editor.commit();
            return true;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        } catch (Exception error) {
            return false;
        }
    }

    /**
     * 获取T数据
     *
     * @param <T>
     * @param context
     * @return
     */
    public static <T> T getObj(Context context, String savingName, Class<T> classType) {
        T user = null;
        try {
            SharedPreferences settings = context.getSharedPreferences(savingName, Context.MODE_PRIVATE);
            String data = settings.getString(classType.getName(), null);

            if(!TextUtils.isEmpty(data)){
                //绑定设备信息，动态生成密码
                String name= new DeviceUuidFactory(context).getDeviceUuid().toString();
                String messageAfterDecrypt = AESCrypt.decrypt(name, data);
                Gson gson =  new Gson();
                user = gson.fromJson(messageAfterDecrypt, classType);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return user;

    }

    /**
     * 清空obj数据
     *
     * @param
     */
    public static <T> Boolean cleanUserInfo(Context context, String savingName, Class<T> classType) {
        SharedPreferences settings = context.getSharedPreferences(savingName, Context.MODE_PRIVATE);
        try {
            T obj = classType.newInstance();
            SharedPreferences.Editor editor = settings.edit();
            Gson gson = new Gson();
            String userdataString = gson.toJson(obj);
            editor.putString(classType.getName(), userdataString);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}