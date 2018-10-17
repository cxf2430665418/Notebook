package com.example.cxf24.note.entity;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户信息管理类
 */
public class AccountUtils {

    private static final String USER_NAME = "user_name";
    private static final String USER_PWD = "pwd";

    /**
     * 保存用户的所有信息
     */

    public static void saveUserInfos(Context context, User user, String user_pwd) {
        saveUserName(context, user.getUsername());
        saveUserPwd(context, user_pwd);

    }
    public static String getUserName(Context context) {
        return (String) SPUtils.get(context, USER_NAME, "");
    }

    public static void saveUserName(Context context, String str) {
        SPUtils.put(context, USER_NAME, str);
    }

    public static String getUserPwd(Context context) {
        return (String) SPUtils.get(context, USER_PWD, "");
    }

    public static void saveUserPwd(Context context, String str) {
        SPUtils.put(context, USER_PWD, str);
    }

    /** public static Map<String,String> getUserInfo(){
        String path = "/data/data/com.example.cxf24.note.entity";
        File userInfo = new File(path,"userInfo.txt");

        try {
            FileInputStream input = new FileInputStream(userInfo);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            String info = buffer.readLine();
            String user_Info[]=info.split("##");
            Map map = new HashMap<String, String>();

            map.put("userName", user_Info[0]);
            map.put("password", user_Info[1]);
            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }**/
}
