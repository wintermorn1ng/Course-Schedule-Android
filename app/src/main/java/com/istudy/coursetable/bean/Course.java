package com.istudy.coursetable.bean;

import java.util.ArrayList;

public class Course {
    private int day;
    private int order;

    private String courseName;
    private String classroom;
    private String teacher;
    private String credit;
    private String examType;

    private int color;
    public int week;
    public boolean isUser;
    public Course() {
    }

    /**
     *
     * @param day 上课天
     * @param order 节次
     * @param courseName 课程名
     */



    public Course(int day, int order, String courseName,String classroom) {
        this.day = day;
        this.order = order;
        this.courseName = courseName;
        this.classroom = classroom;
    }

    /**
     *
     * @param day 上课周几
     * @param order 节次
     * @param courseName 课程名
     * @param classroom 教室
     * @param teacher 任课老师
     */
    public Course(int day, int order, String courseName, String classroom, String teacher) {
        this.day = day;
        this.order = order;
        this.courseName = courseName;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @Override
    public String toString() {
        return "Course{" +
                "day=" + day +
                ", order=" + order +
                ", courseName='" + courseName + '\'' +
                ", classroom='" + classroom + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
