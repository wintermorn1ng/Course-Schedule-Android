package com.istudy.coursetable.ui.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;

public class AlarmHelper {
    AlarmManager alarmManager;
    Context mContext;
    public AlarmHelper(Context context) {
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(int hour, int minute, int second, PendingIntent pi){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
        Log.d("AlarmHelper","闹钟设置成功");
    }
}
