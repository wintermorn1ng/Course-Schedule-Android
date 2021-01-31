package com.istudy.coursetable.ui;

import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.CourseInfo;
import com.istudy.coursetable.bean.CourseInfos;
import com.istudy.coursetable.bean.Courses;
import com.istudy.coursetable.db.CourseRep;
import com.istudy.coursetable.db.SharedPreferencesHelper;
import com.istudy.coursetable.ui.adapter.CoursesViewAdapter;
import com.istudy.coursetable.ui.view.CoursesView;
import com.istudy.coursetable.util.CourseInfos2CourseWeek;
import com.istudy.coursetable.util.GsonUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import butterknife.Action;
import butterknife.BindView;
import butterknife.ButterKnife;
import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.course_view) CoursesView coursesView;
    @BindView(R.id.add_course_btn) Button addCourseBtn;
    @BindView(R.id.get_course_btn) Button getCourseBtn;
    @BindView(R.id.stepperTouch) StepperTouch stepperTouch;
    private CoursesViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);         //绑定主布局


        setTitle("课表");
        initActivity();
        //test();
//        mAdapter.addItem(new Course(2,1,"hahah"));
    }

    private void initActivity(){
        getData();
        int now = SharedPreferencesHelper.getInstance().getNowWeek();
        stepperTouch.setCount(now);
        refreshCoursesView(now);
        SharedPreferencesHelper.getInstance().setNowWeek(5);
        addCourseBtn.setOnClickListener(v-> AddCourseActivity.activityStart(this));
        getCourseBtn.setOnClickListener(v->CourseGetterActivity.activityStart(this));
        stepperTouch.setMinValue(1);
        stepperTouch.setMaxValue(30);
        stepperTouch.setSideTapEnabled(true);
        stepperTouch.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean positive) {
                refreshCoursesView(value);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"activity result");
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    try {
                        String str = data.getStringExtra("course");
                        Course course =  GsonUtil.gson.fromJson(str,Course.class);
                        if (Objects.equals(course.getCourseName(), "") || Objects.equals(course.getClassroom(), ""))return;
                        mAdapter.addItem(course);
                    }
                    catch (NullPointerException e){
                        showMessage("添加课程失败");
                    }

                    //Log.d(TAG,course.toString());
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    try {
                        String courseInfo = data.getStringExtra("course_info");
                        if(courseInfo==null){
                            showMessage("返回了空课表！");
                        }
                        Log.d("Main",courseInfo);
                        showMessage("获取成功!");
                        SharedPreferencesHelper.getInstance().putCourses(courseInfo);
                        CourseRep.courseWeek = CourseInfos2CourseWeek.prase(courseInfo);
                        int now = SharedPreferencesHelper.getInstance().getNowWeek();
                        refreshCoursesView(now);
                        stepperTouch.setCount(now);
                    }
                    catch (NullPointerException e){
                        showMessage("解析课表失败！");
                    }
                }
                break;
            default:
                Log.d(TAG,"ok");
        }
    }

/*
    private Courses initTestCourses(){
        Courses courses = new Courses();
        courses.addCourse(new Course(1,2,"语文语文","新101","1"));
        courses.addCourse(new Course(1,4,"数学","新201","1"));
        courses.addCourse(new Course(2,2,"英语","新102","1"));
        courses.addCourse(new Course(4,1,"编译原理","新103","1"));
        courses.addCourse(new Course(5,1,"高等数学","新103","1"));
        courses.addCourse(new Course(4,2,"哈哈哈","新104","1"));
        courses.addCourse(new Course(5,2,"线性代数","新104","1"));
        return courses;
    }
*/

    private void refreshCoursesView(int now){
        if(now<0){
            coursesView.setAdapter(null);
            coursesView.removeAllViews();
            return ;
        }
        if(CourseRep.courseWeek==null||CourseRep.courseWeek.getCourses(now)==null){
            coursesView.setAdapter(null);
            coursesView.removeAllViews();
            return ;  //TODO 未处理自定义
        }
        if(CourseRep.courseWeek.getCourses(now).getDays()==0){
            coursesView.setAdapter(null);
            coursesView.removeAllViews();
            return ;
        }
        coursesView.setLayoutManager(
                new GridLayoutManager(this,CourseRep.courseWeek.getCourses(now).getDays())
        );
        //coursesView.setLayoutManager(new GridLayoutManager(this,CourseRep.courses.getDays()));
        mAdapter = new CoursesViewAdapter(CourseRep.courseWeek.getCourses(now));
        coursesView.setItemAnimator(new DefaultItemAnimator());
        coursesView.setAdapter(mAdapter);
        //mAdapter.addItem(new Course(3,1,"测试"));
    }

/*
    private void test(){
        String data = GsonUtil.gson.toJson(initTestCourses());
        SharedPreferencesHelper.getInstance().init(this);
        //SharedPreferencesHelper.getInstance().putCourses(gson.toJson(initCourses()));
        Log.d(TAG,data);
    }
*/

    private void getData(){
        SharedPreferencesHelper.getInstance().init(this);
        if(SharedPreferencesHelper.getInstance().getCourses()!=null){
            CourseRep.courseWeek = CourseInfos2CourseWeek.prase(SharedPreferencesHelper.getInstance().getCourses());
        }
    }

    public void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}