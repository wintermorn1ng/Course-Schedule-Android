package com.istudy.coursetable.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.IOError;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourseActivity extends AppCompatActivity {
    @BindView(R.id.confirm_btn)Button confirmBtn;
    @BindView(R.id.cancel_btn)Button cancelBtn;
    @BindView(R.id.day_np)NumberPicker dayNp;
    @BindView(R.id.order_np)NumberPicker orderNp;
    @BindView(R.id.course_name_et)EditText courseNameEt;
    @BindView(R.id.classroom_et) EditText classroomEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("添加课程");
        ButterKnife.bind(this);

        initNp();

        cancelBtn.setOnClickListener(v -> {
            finish();
        });

        confirmBtn.setOnClickListener(v->{
            int day = dayNp.getValue();
            int order = orderNp.getValue();
            String courseName = courseNameEt.getText().toString().trim();
            String classroom = classroomEt.getText().toString().trim();
            Course course = new Course(day,order,courseName,classroom,"");
            //Toast.makeText(this,course.toString(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            Gson gson = new Gson();
            intent.putExtra("course",gson.toJson(course));
            if(!TextUtils.isEmpty(courseName)&&!TextUtils.isEmpty(classroom))
                setResult(RESULT_OK,intent);
            finish();
        });
    }

    protected void initNp(){
        String[] daysData = {"周一","周二","周三","周四","周五","周六"};
        dayNp.setDisplayedValues(daysData);
        dayNp.setMinValue(1);
        dayNp.setMaxValue(daysData.length);

        orderNp.setMaxValue(5);
        orderNp.setMinValue(1);
    }

    /**
     * 启动活动的静态方法
     * @param context
     */
    public static void activityStart(Activity context){
        Intent intent = new Intent(context,AddCourseActivity.class);
        context.startActivityForResult(intent,1);
    }
}