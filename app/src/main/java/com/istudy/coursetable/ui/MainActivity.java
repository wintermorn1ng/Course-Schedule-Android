package com.istudy.coursetable.ui;

import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.Courses;
import com.istudy.coursetable.ui.adapter.CoursesViewAdapter;
import com.istudy.coursetable.ui.view.CoursesView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.course_view) CoursesView coursesView;
    @BindView(R.id.add_course_btn) Button addCourseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);         //绑定主布局
        setTitle("课表");

        initCoursesView();
        addCourseBtn.setOnClickListener(v->{
            Intent intent = new Intent(this,AddCourseActivity.class);
            startActivity(intent);
        });
    }

    private Courses initCourses(){
        Courses courses = new Courses(5);
        courses.addCourse(new Course(1,2,"语文语文","新101","1"));
        courses.addCourse(new Course(1,4,"数学","新201","1"));
        courses.addCourse(new Course(2,2,"英语","新102","1"));
        courses.addCourse(new Course(4,1,"编译原理","新103","1"));
        courses.addCourse(new Course(5,1,"高等数学","新103","1"));
        courses.addCourse(new Course(4,2,"哈哈哈","新104","1"));
        courses.addCourse(new Course(5,2,"线性代数","新104","1"));
        return courses;
    }

    private void initCoursesView(){
        coursesView.setLayoutManager(new GridLayoutManager(this,5));
        CoursesViewAdapter mAdapter = new CoursesViewAdapter(initCourses());
        coursesView.setItemAnimator(new DefaultItemAnimator());
        coursesView.setAdapter(mAdapter);
    }
}