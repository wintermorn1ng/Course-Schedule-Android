package com.istudy.coursetable.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.istudy.coursetable.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourseActivity extends AppCompatActivity {
    @BindView(R.id.confirm_btn)Button confirmBtn;
    @BindView(R.id.cancel_btn)Button cancelBtn;
    @BindView(R.id.day_np)NumberPicker dayNp;
    @BindView(R.id.order_np)NumberPicker orderNp;

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
    }

    protected void initNp(){
        String[] daysData = {"周一","周二","周三","周四","周五","周六"};
        dayNp.setDisplayedValues(daysData);
        dayNp.setMinValue(1);
        dayNp.setMaxValue(daysData.length);

        orderNp.setMaxValue(4);
        orderNp.setMinValue(1);
    }
}