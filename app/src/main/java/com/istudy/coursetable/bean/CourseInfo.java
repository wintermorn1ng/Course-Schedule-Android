package com.istudy.coursetable.bean;

import java.util.ArrayList;

public class CourseInfo {
    public String name;
    public String teacher;
    public String credit;   //学分
    public String examType;   //如   分散（46）
    public ArrayList<Elem> times;

    public CourseInfo() {
        times = new ArrayList<>();
    }

    class Elem{
        int beginWeek;
        int endWeek;
        int isEveryWeek = 0;
        int weekday;
        int order;
        String classroom;

        public Elem(int beginWeek, int endWeek, int isEveryWeek, int weekday, int order, String classroom) {
            this.beginWeek = beginWeek;
            this.endWeek = endWeek;
            this.isEveryWeek = isEveryWeek;
            this.weekday = weekday;
            this.order = order;
            this.classroom = classroom;
        }

        @Override
        public String toString() {
            return "Elem{" +
                    "beginWeek=" + beginWeek +
                    ", endWeek=" + endWeek +
                    ", isEveryWeek=" + isEveryWeek +
                    ", weekday=" + weekday +
                    ", order=" + order +
                    ", classroom='" + classroom + '\'' +
                    '}';
        }
    }


    public void addTimes(int beginWeek,int endWeek,int isWeek,int weekday,int order,String classroom){
        times.add(new Elem(beginWeek,endWeek,isWeek,weekday,order,classroom));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public ArrayList<Elem> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Elem> times) {
        this.times = times;
    }

    public int getBeginWeek(int pos) {
        return times.get(pos).beginWeek;
    }

    public int getEndWeek(int pos) {
        return times.get(pos).endWeek;
    }

    public int getWeekday(int pos) {
        return times.get(pos).weekday;
    }

    public int getOrder(int pos) {
        return times.get(pos).order;
    }

    public String getClassroom(int pos) {
        return times.get(pos).classroom;
    }

    public int getIsEveryWeek(int pos){
        return times.get(pos).isEveryWeek;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credit='" + credit + '\'' +
                ", examType='" + examType + '\'' +
                ", times=" + times.toString() +
                '}';
    }
}
