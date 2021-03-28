package com.istudy.coursetable.ui.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.istudy.coursetable.ui.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == MainActivity.INTENT_ALARM_LOG) {
            Log.d("AlarmReceiver", "log log log");
            new NotificationHelper().showNotification(context);
        }
    }
}
