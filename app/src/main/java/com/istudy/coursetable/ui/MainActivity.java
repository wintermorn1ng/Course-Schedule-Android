package com.istudy.coursetable.ui;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.Courses;
import com.istudy.coursetable.db.CourseRep;
import com.istudy.coursetable.db.SharedPreferencesHelper;
import com.istudy.coursetable.ui.adapter.CoursesViewAdapter;
import com.istudy.coursetable.ui.adapter.OnRecycleItemTouchListener;
import com.istudy.coursetable.ui.view.CoursesView;
import com.istudy.coursetable.util.CourseInfos2CourseWeek;
import com.istudy.coursetable.util.GsonUtil;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.course_view) CoursesView coursesView;
    //@BindView(R.id.stepperTouch) StepperTouch stepperTouch;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.week_sp)
    Spinner weekSp;
    private CoursesViewAdapter mAdapter;
    private PopupMenu popupMenu;
    private int now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);         //绑定主布局
        setTitle("课表");
        initActivity();
    }
    private void initActivity(){
        getData();
        SharedPreferencesHelper.getInstance().setNowWeek(5);
        now = SharedPreferencesHelper.getInstance().getNowWeek();

        ArrayList lists = new ArrayList<String>();
        for(int i=1;i<=30;i++)lists.add("第"+i+"周");
        SpinnerAdapter adapter = new ArrayAdapter(this,R.layout.list_item,lists);
        weekSp.setAdapter(adapter);
        weekSp.setSelection(now-1);
        weekSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshCoursesView(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab.setOnClickListener(v-> showMenu(v,R.menu.popup_menu));
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fab.hide();
                return true;
            }
        });
    }

    private void showMenu(View v, @MenuRes int menuRes){
        if(popupMenu==null){
            popupMenu = new PopupMenu(this,v);
            popupMenu.inflate(menuRes);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.option_1:
                            AddCourseActivity.activityStart(MainActivity.this);
                            break;
                        case R.id.option_2:
                            CourseGetterActivity.activityStart(MainActivity.this);
                            break;
                    }
                    return true;
                }
            });
            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {

                }
            });
        }
        popupMenu.show();
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
                        Log.d("debug",str);
                        Course course =  GsonUtil.gson.fromJson(str,Course.class);
                        if (Objects.equals(course.getCourseName(), "") || Objects.equals(course.getClassroom(), ""))return;
                        mAdapter.addItem(course);
                        course.week = now;
                        course.setColor(R.attr.colorPrimary);
                        course.isUser=true;
                        CourseRep.add(course);
                        SharedPreferencesHelper.getInstance().putUserCourses(GsonUtil.gson.toJson(CourseRep.userCourses));
                    }
                    catch (NullPointerException e){
                        showMessage("添加课程失败");
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    try {
                        String courseInfo = data.getStringExtra("course_info");
                        if(courseInfo==null){
                            showMessage("返回了空课表！");
                        }
                        showMessage("获取成功!");
                        SharedPreferencesHelper.getInstance().putCourses(courseInfo);
                        CourseRep.courseWeek = CourseInfos2CourseWeek.prase(courseInfo);
                        now = SharedPreferencesHelper.getInstance().getNowWeek();
                        refreshCoursesView(now);
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
        for(int i=0;i<CourseRep.userCourses.size();i++){
            if(CourseRep.userCourses.get(i).week==now){
                mAdapter.addUserCourse(CourseRep.userCourses.get(i));
            }
        }
        coursesView.setItemAnimator(new DefaultItemAnimator());
        coursesView.setAdapter(mAdapter);
        coursesView.addOnItemTouchListener(new OnRecycleItemTouchListener(coursesView){
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                super.onItemClick(vh, position);

            }
        });
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
        if(SharedPreferencesHelper.getInstance().getUserCourses()!=null){
            CourseRep.userCourses = GsonUtil.gson.fromJson(SharedPreferencesHelper.getInstance().getUserCourses(), Courses.class);
            Log.d("debug","??????");
        }
        else{
            CourseRep.userCourses = new Courses();
        }
    }

    public void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}