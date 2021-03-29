package com.istudy.coursetable.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonSyntaxException;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.BaseWeek;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.CourseInfos;
import com.istudy.coursetable.bean.Courses;
import com.istudy.coursetable.db.CourseRep;
import com.istudy.coursetable.db.SharedPreferencesHelper;
import com.istudy.coursetable.ui.adapter.CoursesViewAdapter;
import com.istudy.coursetable.ui.adapter.OnRecycleItemTouchListener;
import com.istudy.coursetable.ui.view.CoursesView;
import com.istudy.coursetable.util.CourseInfos2CourseWeek;
import com.istudy.coursetable.util.GetInfoUtil;
import com.istudy.coursetable.util.GsonUtil;
import com.istudy.coursetable.util.HTML2CourseRepUtil;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableFrag extends Fragment {
    @BindView(R.id.course_view)
    CoursesView coursesView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.week_sp)
    Spinner weekSp;
    private CoursesViewAdapter mAdapter;
    private PopupMenu popupMenu;
    private int now = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flag_table,container,false);
        ButterKnife.bind(this,view);         //绑定主布局
        getData();
        String str = SharedPreferencesHelper.getInstance().getNowWeek();
        if(str != ""){
            BaseWeek baseWeek = GsonUtil.gson.fromJson(str,BaseWeek.class);
            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            //Date star = new Date(2021-1900,2,30);
            //int nowWeek = (int) (baseWeek.getNowWeek(star));
            int nowWeek = (int) (baseWeek.getNowWeek(new Date(System.currentTimeMillis())));
            //Log.d("Debug", String.valueOf(nowWeek));
            //Log.d("Debug", String.valueOf(baseWeek.week));
            now = nowWeek%30;
        }
        initView();
        return view;
    }

    private void initView(){
        ArrayList lists = new ArrayList<String>();
        for(int i=1;i<=30;i++)lists.add("第"+i+"周");
        SpinnerAdapter adapter = new ArrayAdapter(getContext(),R.layout.list_item,lists);
        weekSp.setAdapter(adapter);
        weekSp.setSelection(now-1);
        weekSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshCoursesView(position+1);
                now = position+1;
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

    private void getData(){
        SharedPreferencesHelper.getInstance().init(getContext());
        if(SharedPreferencesHelper.getInstance().getCourses()!=null){
            CourseInfos courseInfos = null;
            try {
                courseInfos = GsonUtil.gson.fromJson(SharedPreferencesHelper.getInstance().getCourses(),CourseInfos.class);
            }
            catch (JsonSyntaxException e){
                e.printStackTrace();
                SharedPreferencesHelper.getInstance().putCourses(null);
            }
            CourseRep.courseWeek = CourseInfos2CourseWeek.parse(courseInfos);
        }
        if(SharedPreferencesHelper.getInstance().getUserCourses()!=null){
            CourseRep.userCourses = GsonUtil.gson.fromJson(SharedPreferencesHelper.getInstance().getUserCourses(), Courses.class);
        }
        else{
            CourseRep.userCourses = new Courses();
        }
    }
    private void showMenu(View v, @MenuRes int menuRes){
        if(popupMenu==null){
            popupMenu = new PopupMenu(getContext(),v);
            popupMenu.inflate(menuRes);
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId()==R.id.option_1){
                    AddCourseFragment addCourseFragment = new AddCourseFragment();
                    addCourseFragment.show(getParentFragmentManager(),"tag");
                    addCourseFragment.setConfirmButton(v1 ->{
                        int day = addCourseFragment.getDayandOrder().first;
                        int order = addCourseFragment.getDayandOrder().second;
                        String courseName = addCourseFragment.getCourseName();
                        String classroom = addCourseFragment.getClassroom();
                        setUserCurses(new Course(day,order,courseName,classroom));
                        addCourseFragment.dismiss();
                    });
                    //AddCourseActivity.activityStart(getActivity());
                }
                else if(item.getItemId()==R.id.option_2){
                    GetCourseFragment getCourseFragment = new GetCourseFragment(this);
                    getCourseFragment.show(getParentFragmentManager(),"tag");
                    getCourseFragment.setOkButton(e->{
                        String username = getCourseFragment.getUsername();
                        String password = getCourseFragment.getPassword();
                        String captcha = getCourseFragment.getCaptcha();
                        GetInfoUtil.getInstance().getCourseTable(getCourseFragment,username,password,captcha);
                    });
                    //CourseGetterActivity.activityStart(getActivity());
                }
                return true;
            });
            popupMenu.setOnDismissListener(menu -> {

            });
        }
        popupMenu.show();
    }
    private void showMessage(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
    private void refreshCoursesView(int now){
        weekSp.setSelection(now-1);
        if(now<0){
            coursesView.setAdapter(null);
            coursesView.removeAllViews();
            return ;
        }
        if(CourseRep.courseWeek==null||CourseRep.courseWeek.getCourses(now)==null){
            coursesView.setAdapter(null);
            coursesView.removeAllViews();
            return ;
        }
        if(CourseRep.courseWeek.getCourses(now).getDays()==0){
            coursesView.setAdapter(null);
            coursesView.removeAllViews();
            return ;
        }


        //TODO   bug here
        coursesView.setLayoutManager(
                new GridLayoutManager(getContext(),CourseRep.courseWeek.getCourses(now).getDays())
        );
        mAdapter = new CoursesViewAdapter(CourseRep.courseWeek.getCourses(now));
        for(int i=0;i<CourseRep.userCourses.getListSize();i++){
            if(CourseRep.userCourses.getList(i).week==now){
                mAdapter.addUserCourse(CourseRep.userCourses.getList(i));
            }
        }
        coursesView.setItemAnimator(new DefaultItemAnimator());
        coursesView.setAdapter(mAdapter);
        coursesView.addOnItemTouchListener(new OnRecycleItemTouchListener(coursesView){
            @Override
            public void onItemClick(CoursesView.ViewHolder vh, int position) {
                super.onItemClick(vh, position);
                if(mAdapter.getCourse(position)!=null){
                    ShowCourseInfoFrag showCourseInfoFrag = new ShowCourseInfoFrag(mAdapter.getCourse(position));
                    showCourseInfoFrag.show(getParentFragmentManager(),"tag"+position+10);
                    //showCourseInfoFrag.setData(mAdapter.getCourse(position));
                    //ShowCourseInfoActivity.activityStart(getContext(),mAdapter.getCourse(position));
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
                super.onItemLongClick(vh, position);
                if(mAdapter.getCourse(position)!=null&&mAdapter.getCourse(position).isUser){
                    tryDeleteCourse(mAdapter.getCourse(position));
                }
            }
        });
    }
    private void tryDeleteCourse(Course course){
        new AlertDialog.Builder(getContext()).setTitle("确认删除课程？")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CourseRep.remove(course);
                        SharedPreferencesHelper.getInstance().putUserCourses(GsonUtil.gson.toJson(CourseRep.userCourses));
                        mAdapter.removeItem(course);

                    }
                })
                .setNegativeButton("取消", (dialog, which) -> { }).show();
    }


    //TODO bug here  根据自定义添加课程的天数可能会更新网格列数
    public void setUserCurses(Course course){
        mAdapter.addItem(course);
        course.week = now;
        course.setColor(R.attr.colorPrimary);
        course.isUser=true;
        CourseRep.add(course);
        SharedPreferencesHelper.getInstance().putUserCourses(GsonUtil.gson.toJson(CourseRep.userCourses));
    }
    public void setCourses(String str){
        String[] strs = new String[2];
        int c = str.indexOf("$$$$$$$$$$");
        strs[0] = str.substring(0,c);
        strs[1] = str.substring(c+10);
        ArrayList list = HTML2CourseRepUtil.parse(strs[1]);
        CourseInfos courseInfos = new CourseInfos(list);
        SharedPreferencesHelper.getInstance().putCourses(GsonUtil.gson.toJson(courseInfos));
        CourseRep.courseWeek = CourseInfos2CourseWeek.parse(courseInfos);

        // TODO 设置当前周
        now = HTML2CourseRepUtil.parseNow(str);
        Calendar cd = Calendar.getInstance();
        int week = cd.get(Calendar.DAY_OF_WEEK)-1;
        if(week == 0)week = 7;
        Log.d("DEBUG_??", String.valueOf(week));
        cd.add(Calendar.DATE,-1*(week-1));
        long base = cd.getTimeInMillis();
        Log.d("DEBUG_???", String.valueOf(cd.get(Calendar.DATE)));
        BaseWeek baseWeek = new BaseWeek(base,now);
        SharedPreferencesHelper.getInstance().setNowWeek(GsonUtil.gson.toJson(baseWeek));
        refreshCoursesView(now);
    }

    //TODO 错误处理
    public void processData(String data){
        setCourses(data);
    }

}
