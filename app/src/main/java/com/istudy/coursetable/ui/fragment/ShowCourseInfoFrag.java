package com.istudy.coursetable.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;

public class ShowCourseInfoFrag extends DialogFragment {
    private TextView teacher_tv ;
    private TextView credit_tv ;
    private TextView exam_tv ;
    private TextView classroom_tv ;
    private Button button ;
    private MaterialToolbar toolbar;
    private Course mCourse;

    public ShowCourseInfoFrag(Course course) {
        super();
        mCourse = course;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activiyt_show_course_info,container);
        teacher_tv = root.findViewById(R.id.teacher_tv);
        credit_tv = root.findViewById(R.id.credit_tv);
        exam_tv = root.findViewById(R.id.exam_tv);
        classroom_tv = root.findViewById(R.id.classroom_vt);
        button = root.findViewById(R.id.cancel_btn);
        toolbar = root.findViewById(R.id.title);
        button.setOnClickListener(v->dismiss());
        setData(mCourse);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Window window = getDialog().getWindow();
        // ViewGroup.LayoutParams attributes = window.getAttributes();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public void setData(Course course){
        toolbar.setTitle(course.getCourseName());
        toolbar.setBackgroundColor(course.getColor());
        classroom_tv.setText("教室："+course.getClassroom());
        if(course.isUser==false) {
            teacher_tv.setText("教师：" + course.getTeacher());
            exam_tv.setText("考核方式：" + course.getExamType());
            credit_tv.setText("学分：" + course.getCredit());
        }
    }
}
