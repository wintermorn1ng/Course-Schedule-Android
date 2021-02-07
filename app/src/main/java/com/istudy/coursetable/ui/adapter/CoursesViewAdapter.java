package com.istudy.coursetable.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.Courses;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {

    private Courses mCourses;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;
        TextView classroom;
        MaterialCardView materialCardView;
        public ViewHolder(View view){
            super(view);
            courseName =view.findViewById(R.id.course_name);
            classroom =view.findViewById(R.id.classroom);
            materialCardView = view.findViewById(R.id.main_cv);
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
        if(course==null){
            holder.materialCardView.setCardBackgroundColor(0xFFEEEEEE);
            holder.materialCardView.setClickable(false);
            holder.courseName.setText(null);
            holder.classroom.setText(null);
            return;
        }
        //Log.d("debug",course.getCourseName()+" "+course.getClassroom());

        holder.courseName.setText(course.getCourseName());
        holder.classroom.setText(course.getClassroom());
        holder.materialCardView.setCardBackgroundColor(course.getColor());
        holder.materialCardView.setClickable(true);
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void addItem(Course course){
        mCourses.addCourse(course);
        int position = (course.getOrder()-1)*mCourses.getDays()+course.getDay()-1;
        //notifyItemInserted(position);
        notifyItemChanged(position,course);
    }
    public void removeItem(Course course){
        int position = (course.getOrder()-1)*mCourses.getDays()+course.getDay()-1;
        mCourses.removeCourse(course);
        notifyItemChanged(position);
    }

    public void addUserCourse(Course course){
        mCourses.addCourse(course);
    }

    public Course getCourse(int pos){
        return mCourses.get(pos);
    }
}
