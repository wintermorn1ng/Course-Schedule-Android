package com.istudy.coursetable.util;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.istudy.coursetable.bean.Course;
import com.istudy.coursetable.bean.CourseInfo;
import com.istudy.coursetable.bean.CourseInfos;
import com.istudy.coursetable.bean.CourseWeek;

public class CourseInfos2CourseWeek {
    private static int[] colors = new int[]{0xFF85B8CB,0xFF1D6A96,0xFF6A92CC,0xFF5954A4,0xFF448AFF,0xFF009688};
    private static int colorPos = 0;
    public static CourseWeek parse(CourseInfos courseInfos){
        if(courseInfos==null) return null;
        CourseWeek courseWeek = new CourseWeek();
        for(int i=0;i<courseInfos.list.size();i++){
            CourseInfo temp = courseInfos.list.get(i);
            for(int j=0;j<temp.times.size();j++){
                Course course = new Course();
                course.setDay(temp.getWeekday(j));
                course.setOrder(temp.getOrder(j));
                course.setCourseName(temp.name);
                course.setClassroom(temp.getClassroom(j));
                course.setExamType(temp.examType);
                course.setCredit(temp.credit);
                course.setTeacher(temp.teacher);
                course.setColor(colors[colorPos]);
                int isWeek = temp.getIsEveryWeek(j);
                for(int _i = temp.getBeginWeek(j);_i<=temp.getEndWeek(j);_i++){
                    if(_i>0&&_i<30){
                        if(isWeek==0)
                            courseWeek.addCourseToCourses(_i,course);
                        else if(isWeek==1&&_i%2!=0)
                            courseWeek.addCourseToCourses(_i,course);
                        else if(isWeek==2&&_i%2==0){
                            courseWeek.addCourseToCourses(_i,course);
                        }
                    }
                }
                if(j==temp.times.size()-1){
                    colorPos++;
                    colorPos%=colors.length;
                }
            }
        }

        return courseWeek;
    }
}
