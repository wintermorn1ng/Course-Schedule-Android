package com.istudy.coursetable.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.util.GsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourseActivity extends AppCompatActivity {
    @BindView(R.id.confirm_btn)Button confirmBtn;
    @BindView(R.id.cancel_btn)Button cancelBtn;
    @BindView(R.id.day_np)NumberPicker dayNp;
    @BindView(R.id.order_np)NumberPicker orderNp;
    @BindView(R.id.course_name_et) TextInputEditText courseNameEt;
    @BindView(R.id.classroom_et) TextInputEditText classroomEt;
    @BindView(R.id.course_name_ty)
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle(null);
        ButterKnife.bind(this);

        initNp();

        cancelBtn.setOnClickListener(v -> finish());

        confirmBtn.setOnClickListener(v->{
            int day = dayNp.getValue();
            int order = orderNp.getValue();
            String courseName = courseNameEt.getText().toString().trim();
            String classroom = classroomEt.getText().toString().trim();
            Course course = new Course(day,order,courseName,classroom,"");
            if(!TextUtils.isEmpty(courseName)&&!TextUtils.isEmpty(classroom)){
                Intent intent = new Intent();
                intent.putExtra("course", GsonUtil.gson.toJson(course));
                setResult(RESULT_OK,intent);
            }
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
     */
    public static void activityStart(Activity context){
        Intent intent = new Intent(context,AddCourseActivity.class);
        context.startActivityForResult(intent,1);
    }
}