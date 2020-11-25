package com.istudy.coursetable.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.Courses;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {

    private Courses mCourses;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;
        TextView classroom;

        public ViewHolder(View view){
            super(view);
            courseName =view.findViewById(R.id.course_name);
            classroom =view.findViewById(R.id.classroom);
        }
    }

    public CoursesViewAdapter(Courses mCourses) {
        this.mCourses = mCourses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Course course = mCourses.get(position);
        //Log.d("debug",position+"");
        if(course==null)return;
        //Log.d("debug",course.getCourseName()+" "+course.getClassroom());

        holder.courseName.setText(course.getCourseName());
        holder.classroom.setText(course.getClassroom());

        //if(course.getClassroom()!=null)
            //holder.classroom.setText(course.getClassroom());
        //else holder.classroom.setText("");
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }
}
