package com.istudy.coursetable.bean;

import android.util.Log;

import java.util.ArrayList;

public class Courses {
    private int days;
    ArrayList<Course> courseList;

    public Courses(int days, ArrayList<Course> courseList) {
        this.days = days;
        this.courseList = courseList;
    }

    public Courses(int days) {
        this.days = days;
        courseList = new ArrayList<Course>();
    }

    public void addCourse(Course course){
        courseList.add(course);
    }
    public Course get(int pos){
        int col,row;
        row = (pos+days)/days;
        col = (pos)%days+1;
        for(Course course:courseList){
            //Log.d("debug dd",row+" "+col);
            if(course.getDay()==col&&course.getOrder()==row)return course;
        }
        return null;
    }
    public int size(){
        return days*4;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i=0;i<courseList.size();i++){
            res.append(courseList.get(i).getCourseName()).append(" ").append(courseList.get(i).getClassroom()).append(";");
        }
        return res.toString();
    }
}
