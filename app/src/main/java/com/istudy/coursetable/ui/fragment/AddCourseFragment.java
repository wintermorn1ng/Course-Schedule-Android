package com.istudy.coursetable.ui.fragment;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.istudy.coursetable.R;


public final class AddCourseFragment extends DialogFragment {
    private NumberPicker dayNp;
    private NumberPicker orderNp;
    private TextInputEditText courseNameEt;
    private TextInputEditText classroomEt;
    private View.OnClickListener onClickListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add_course,container);
        Button confirmButton = root.findViewById(R.id.confirm_btn);
        dayNp = root.findViewById(R.id.day_np);
        orderNp = root.findViewById(R.id.order_np);
        courseNameEt = root.findViewById(R.id.course_name_et);
        classroomEt = root.findViewById(R.id.classroom_et);
        confirmButton.setOnClickListener(onClickListener);
        Button cancelButton = root.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(v->{
            dismiss();
        });
        initNp();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
       // ViewGroup.LayoutParams attributes = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public Pair<Integer,Integer> getDayandOrder(){
        return new Pair<>(dayNp.getValue(),orderNp.getValue());
    }
    public String getCourseName(){
        if(courseNameEt.getText()==null)return null;
        return courseNameEt.getText().toString().trim();
    }
    public String getClassroom(){
        if(classroomEt.getText()==null) return null;
        return classroomEt.getText().toString().trim();
    }

    public void setConfirmButton(View.OnClickListener v){
        onClickListener = v;
    }

    private void initNp(){
        String[] daysData = {"周一","周二","周三","周四","周五","周六"};
        dayNp.setDisplayedValues(daysData);
        dayNp.setMinValue(1);
        dayNp.setMaxValue(daysData.length);

        orderNp.setMaxValue(5);
        orderNp.setMinValue(1);
    }
}
