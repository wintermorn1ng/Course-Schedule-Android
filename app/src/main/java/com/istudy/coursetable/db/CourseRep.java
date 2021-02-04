package com.istudy.coursetable.db;

import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.CourseWeek;
import com.istudy.coursetable.bean.Courses;

public class CourseRep {
    public static Courses userCourses;

    public static CourseWeek courseWeek;

    public static void add(Course course){
        userCourses.addCourse(course);
    }
}
