package com.istudy.coursetable.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.istudy.coursetable.R;

public class CourseView extends LinearLayout {
    public CourseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.course_view,this);
    }
}
