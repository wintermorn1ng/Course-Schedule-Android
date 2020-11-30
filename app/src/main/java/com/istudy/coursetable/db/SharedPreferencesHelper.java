package com.istudy.coursetable.db;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringDef;

import com.istudy.coursetable.bean.Courses;

public class SharedPreferencesHelper {
    private SharedPreferences preferences;
    private static SharedPreferencesHelper instance = new SharedPreferencesHelper();
    private SharedPreferences.Editor editor;
    protected SharedPreferencesHelper(){}

    public static SharedPreferencesHelper getInstance(){
        return instance;
    }

    public void init(Context context){
        preferences =  context.getSharedPreferences("course",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void putCourses(String data){
        editor.putString("courses",data);
        editor.commit();
    }

    public void putUserCourses(String data){
        editor.putString("user_courses","test");
        editor.commit();
    }

    public String getCourses(){
        return  preferences.getString("courses","null");
    }

    public String getUserCourses(){
        return preferences.getString("user_courses","null");
    }

}
