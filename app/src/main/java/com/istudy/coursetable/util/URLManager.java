package com.istudy.coursetable.util;

public class URLManager {
    public static final String HOST = "http://jwzx.hrbust.edu.cn";
    public static final String URL_getCaptcha = HOST+"/academic/getCaptcha.do?";
    public static final String URL_checkCaptcha = HOST+"/academic/checkCaptcha.do?";
    public static final String URL_login = HOST+"/academic/j_acegi_security_check?";
    public static final String URL_gotoClassTableLocation = HOST+"/academic/accessModule.do?moduleId=2000&groupId=&randomString=";
    public static final String URL_getNowWeekTable = HOST+"/academic/manager/coursearrange/studentWeeklyTimetable.do?";
    public static final String URL_getWeekTableByWeek = HOST+"/academic/manager/coursearrange/studentWeeklyTimetable.do ";
    public static final String URL_mainPage= HOST+"/academic/index_new.jsp";
    public static final String URL_loginOut= HOST+"/academic/logout_security_check";
}
