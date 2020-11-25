package com.istudy.coursetable.ui;

import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.Courses;
import com.istudy.coursetable.ui.adapter.CoursesViewAdapter;
import com.istudy.coursetable.ui.view.CoursesView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.course_view) CoursesView coursesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);         //绑定主布局

        coursesView.setLayoutManager(new GridLayoutManager(this,5));
        CoursesViewAdapter mAdapter = new CoursesViewAdapter(initCourses());
        //Log.d(TAG,"debug:");
        //Log.d(TAG,initCourses().toString());
        coursesView.setItemAnimator(new DefaultItemAnimator());
        coursesView.setAdapter(mAdapter);
    }

    private Courses initCourses(){
        Courses courses = new Courses(5);
        courses.addCourse(new Course(1,2,"语文","新101","1"));
        courses.addCourse(new Course(1,4,"数学","新201","1"));
        courses.addCourse(new Course(2,2,"英语","新102","1"));
        courses.addCourse(new Course(4,1,"拉力","新103","1"));
        courses.addCourse(new Course(5,1,"赛车","新103","1"));
        courses.addCourse(new Course(4,2,"我去","新104","1"));
        courses.addCourse(new Course(5,2,"我去","新104","1"));
        return courses;
    }
}