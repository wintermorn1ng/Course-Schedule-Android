package com.istudy.coursetable.bean;

import android.util.Log;

import java.util.ArrayList;

public class Courses {
    private int days = 0;
    private int maxOrder = 0;
    ArrayList<Course> courseList;

    public int getDays() {
        return days;
    }

    public int getMaxOrder() {
        return maxOrder;
    }

    public Courses(int days, ArrayList<Course> courseList) {
        this.days = days;
        this.courseList = courseList;
    }

    public Courses() {
        courseList = new ArrayList<>();
    }

    public void addCourse(Course course){
        days = Math.max(course.getDay(),days);
        maxOrder = Math.max(course.getOrder(),maxOrder);
        courseList.add(0,course);
    }
    public void removeCourse(Course course){
        courseList.remove(course);
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
        return days*maxOrder;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i=0;i<courseList.size();i++){
            res.append(courseList.get(i).getCourseName()).append(" ").append(courseList.get(i).getClassroom()).append(";");
        }
        return res.toString();
    }
    public Course getList(int pos){
        return courseList.get(pos);
    }
    public int getListSize(){
        return courseList.size();
    }
}
