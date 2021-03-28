package com.istudy.coursetable.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.istudy.coursetable.R;
import com.istudy.coursetable.db.CourseRep;
import com.istudy.coursetable.db.SharedPreferencesHelper;
import com.istudy.coursetable.util.GsonUtil;

public class SettingFrag extends PreferenceFragmentCompat {
    private Preference clearCustomCourse;
    private Preference firstClass;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting,rootKey);

        firstClass = findPreference("first_class");
        clearCustomCourse = findPreference("clear_custom_course");
        initClear();
        initClassTime();
    }
    private void initClassTime(){
        firstClass.setOnPreferenceClickListener(v->{
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(10)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                    .build();
            materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstClass.setSummary(materialTimePicker.getHour()+":"+materialTimePicker.getMinute());
                }
            });
            materialTimePicker.show(getParentFragmentManager(), "fragment_tag");
            return true;
        });
    }
    private void initClear(){
        clearCustomCourse.setOnPreferenceClickListener(v->{
            clearCustomCourse();
            return true;
        });
    }
    private void clearCustomCourse(){
        new AlertDialog.Builder(getContext()).setTitle("确认清空自定义课程？")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesHelper.getInstance().clearCustomCourse();
                        Toast.makeText(getContext(),"删除成功，将在下次启动应用时生效",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", (dialog, which) -> { }).show();
    }
}
