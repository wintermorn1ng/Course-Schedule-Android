package com.istudy.coursetable.util;

import android.util.Pair;

import com.istudy.coursetable.bean.CourseInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HTML2CourseRepUtil {
    private static Pair<Integer,Integer> getWeek(String str){
        int mx=0,mn=0;int i=0;for(;i<str.length()&&str.charAt(i)!='-';i++){
            mx*=10;mx+= str.charAt(i)-'0'; } i++;
        for(;i<str.length()&&str.charAt(i)<='9'&&str.charAt(i)>='0';i++){
            mn*=10;mn+= str.charAt(i)-'0'; }
        return new Pair(mx,mn);
    }
    private static int getWeekday(String str){
        String[] set = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        for(int i=0;i<set.length;i++){ if(set[i].equals(str)){return i+1; } }
        return 0;
    }

    private static int getOrder(String str){
        String[] set = {"第一大节","第二大节","第三大节","第四大节","第五大节","第六大节","第七大节"};
        for(int i=0;i<set.length;i++){ if(set[i].equals(str)){return i+1; } }
        return 0;
    }

    public static ArrayList<CourseInfo> parse(String data){
        Document doc = Jsoup.parse(data);
        Elements tables = doc.getElementsByTag("table");
        if(tables==null)return null;
        if(tables.select(".infolist_tab")==null) return null;
        Element table = tables.select(".infolist_tab").get(0);
        if(table==null){ return null; }


        Elements trs = table.getElementsByClass("infolist_common");
        if(trs==null) return null;
        ArrayList<CourseInfo> dataSet = new ArrayList<>();
        for(Element i:trs){                                               // 每个i是一门课
            Elements tds = i.select(".infolist_common > td");
            if(tds==null)return null;
            if(tds.size()<9)continue;
            CourseInfo course = new CourseInfo();
            if(tds.get(5).text().trim().equals("任选"))continue;
            course.setName(tds.get(2).text().trim().replace("（","\n(").replace("）",")"));
            course.setTeacher(tds.get(3).text().trim());
            course.setCredit(tds.get(4).text().trim());
            course.setExamType(tds.get(6).text().trim());
            Elements _trs = tds.get(9).select("tr");
            if(_trs==null)continue;
            for(Element k:_trs){
                Elements _tds = k.select("td");
                if(_tds.size()<4)continue;
                int mx = getWeek(_tds.get(0).text().trim()).first;
                int mn = getWeek(_tds.get(0).text().trim()).second;
                int isWeek=0;
                if(_tds.get(0).text().contains("单周"))isWeek=1;
                if(_tds.get(0).text().contains("双周"))isWeek=2;
                int weekDay = getWeekday(_tds.get(1).text().trim());
                int order = getOrder(_tds.get(2).text().trim());
                course.addTimes(mx,mn,isWeek,weekDay,order,_tds.get(3).text().replace("（","(").replace("）",")"));
            }
            dataSet.add(course);
        }
        return dataSet;
    }
}
