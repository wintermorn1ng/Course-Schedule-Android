package com.istudy.coursetable.ui;

import com.google.gson.Gson;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.Courses;
import com.istudy.coursetable.db.CourseRep;
import com.istudy.coursetable.db.SharedPreferencesHelper;
import com.istudy.coursetable.ui.adapter.CoursesViewAdapter;
import com.istudy.coursetable.ui.view.CoursesView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.course_view) CoursesView coursesView;
    @BindView(R.id.add_course_btn) Button addCourseBtn;
    private CoursesViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);         //绑定主布局
        setTitle("课表");

        getData();
        initCoursesView();
        mAdapter.addItem(new Course(2,1,"hahah"));
        addCourseBtn.setOnClickListener(v-> AddCourseActivity.activityStart(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"???");
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String str = data.getStringExtra("course");
                    Gson gson = new Gson();
                    Course course =  gson.fromJson(str,Course.class);
                    if (course.getCourseName()==""||course.getClassroom()=="")return;
                    mAdapter.addItem(course);
                    //Log.d(TAG,course.toString());
                }
                break;
            default:
                Log.d(TAG,"ok");
        }
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
        coursesView.setLayoutManager(new GridLayoutManager(this,CourseRep.courses.getDays()));
        mAdapter = new CoursesViewAdapter(CourseRep.courses);
        coursesView.setItemAnimator(new DefaultItemAnimator());
        coursesView.setAdapter(mAdapter);
        //mAdapter.addItem(new Course(3,1,"测试"));
    }

    private void test(){
        Gson gson = new Gson();
        String data = gson.toJson(initCourses());
        SharedPreferencesHelper.getInstance().init(this);
        Log.d(TAG,data);
    }

    private void getData(){
        Gson gson = new Gson();
        SharedPreferencesHelper.getInstance().init(this);
        CourseRep.courses = gson.fromJson(SharedPreferencesHelper.getInstance().getCourses(),Courses.class);
    }
}