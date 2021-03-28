package com.istudy.coursetable.bean;

import android.util.Log;

import java.sql.Date;

public class BaseWeek {
    public long base;
    public int week;

    public BaseWeek(long base, int week) {
        this.base = base;
        this.week = week;
    }
    public long getNowWeek(Date now){
        long n = now.getTime();
        long num = n-base;
        long days = num/24/60/60/1000;
        long ans = days/7;
        Log.d("Debug",ans+" "+days);
        return ans+week;
    }

}
