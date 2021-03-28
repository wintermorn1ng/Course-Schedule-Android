package com.istudy.coursetable.bean;

import java.util.ArrayList;


public class CourseWeek {
    private ArrayList<Courses> data;

    public CourseWeek(ArrayList<Courses> data) {
        this.data = data;
    }


    public CourseWeek() {
        data = new ArrayList<>();
        for(int i=0;i<30;i++){
            data.add(new Courses());
        }
    }

    public Courses getCourses(int week){
        if(week>data.size())return null;
        return data.get(week-1);
    }

    public void setCourse(Courses course,int week){
        data.add(week-1,course);
    }

    public void addCourseToCourses(int week,Course course){
       // Log.d(TAG, "addCourseToCourses: "+(week-1));
        data.get(week-1).addCourse(course);
    }
}
