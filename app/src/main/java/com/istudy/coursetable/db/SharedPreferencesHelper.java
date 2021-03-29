package com.istudy.coursetable.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesHelper {
    private SharedPreferences preferences;
    private static SharedPreferencesHelper instance = new SharedPreferencesHelper();
    private SharedPreferences.Editor editor;
    protected SharedPreferencesHelper(){}

    public static SharedPreferencesHelper getInstance(){
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    public void init(Context context){
        preferences =  context.getSharedPreferences("course",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void putCourses(String data){
        editor.putString("courses",data);
        editor.commit();
    }

    public void putUserCourses(String data){
        editor.putString("user_courses",data);
        editor.commit();
    }

    public String getCourses(){
        if(preferences.contains("courses"))
            return  preferences.getString("courses","null");
        else return null;
    }

    public String getUserCourses(){
        if(preferences.contains("user_courses"))
            return preferences.getString("user_courses","null");
        else return null;
    }

    public void setNowWeek(String val){
        editor.putString("now_week",val);
        editor.commit();
    }

    public String getNowWeek(){
        return preferences.getString("now_week","");
    }

    public void clearCustomCourse(){
        editor.putString("user_courses",null);
        editor.commit();
    }

    public void setPictureList(String str){
        editor.putString("picture_list",str);
        editor.commit();
    }
    public String getPictureList(){
        if(preferences.contains("picture_list"))
            return preferences.getString("picture_list","");
        return "";
    }
}
