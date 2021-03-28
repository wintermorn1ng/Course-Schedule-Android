package com.istudy.coursetable.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.util.GsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

@Deprecated
public class ShowCourseInfoActivity extends AppCompatActivity {
/*
    @BindView(R.id.teacher_tv) TextView teacher_tv;
    @BindView(R.id.credit_tv) TextView credit_tv;
    @BindView(R.id.exam_tv) TextView exam_tv;
    @BindView(R.id.classroom_vt) TextView classroom_tv;
    @BindView(R.id.cancel_btn) Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiyt_show_course_info);
        ButterKnife.bind(this);
        button.setOnClickListener(v->{finish();});
        Intent intent = getIntent();
        try{
            String str = intent.getStringExtra("course_info");
            Course course = GsonUtil.gson.fromJson(str,Course.class);
            setTitle(course.getCourseName());
            classroom_tv.setText("教室："+course.getClassroom());
            if(course.isUser==false){
                teacher_tv.setText("教师："+course.getTeacher());
                exam_tv.setText("考核方式："+course.getExamType());
                credit_tv.setText("学分："+course.getCredit());
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
*/
/*
    public static void activityStart(Context context, Course course){
        Intent intent = new Intent(context,ShowCourseInfoActivity.class);
        intent.putExtra("course_info", GsonUtil.gson.toJson(course));
        context.startActivity(intent);
    }
*/
}
