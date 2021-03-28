package com.istudy.coursetable.bean;

import java.util.ArrayList;

public class CourseInfos {
    public ArrayList <CourseInfo> list;
    public CourseInfos(ArrayList<CourseInfo> list) {
        this.list = list;
    }

    public CourseInfos() {
        list = new ArrayList<>();
    }
}
