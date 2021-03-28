package com.istudy.coursetable.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil extends OkhttpUtil {
    private final String TAG = "HTTPUTIL";
    protected Bitmap getCaptcha() throws IOException {
        String target = URLManager.URL_getCaptcha+Math.random();
        Request request = new Request.Builder().url(target).header("User-Agent",USERAGENT).build();
        Response response = okHttpClient.newCall(request).execute();
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

    protected boolean checkCaptcha(String captchaCode) throws IOException{
        RequestBody requestBody = new FormBody.Builder().add("captchaCode",captchaCode).build();
        Request request = new Request.Builder().post(requestBody).url(URLManager.URL_checkCaptcha)
                .header("User-Agent",USERAGENT)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string().equals("true");
    }

    protected String login(String username,String password,String captchaCode) throws IOException{
        RequestBody requestBody = new FormBody.Builder()
                .add("j_username",username)
                .add("j_password",password)
                .add("j_captcha",captchaCode)
                .add("login","")
                .build();
        Request request = new Request.Builder().post(requestBody).url(URLManager.URL_login)
                .header("User-Agent",USERAGENT)
                .build();
        Response response = okHttpClient.newCall(request).execute();
            String str = response.request().url().toString();
            if(str.equals("http://jwzx.hrbust.edu.cn/academic/index_new.jsp"))
                return  response.networkResponse().request().headers("Cookie").get(0);
        return "login__false__";
    }

    protected String getClassTable() throws IOException {
        String url = URLManager.URL_gotoClassTableLocation;
        url = url + getTime() + getRandomString(6);
        Request request = new Request.Builder().url(url)
                .header("User-Agent",USERAGENT)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    protected String getWeek() throws IOException{
        String url = URLManager.URL_getNowWeekTable;
        RequestBody requestBody = new FormBody.Builder()
                .add("yearid",String.valueOf(getYear()))
                .add("termid",getTerm())
                .build();
        Request request = new Request.Builder().post(requestBody).url(URLManager.URL_getNowWeekTable)
                .header("User-Agent",USERAGENT)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String str = response.request().url().toString();
        return response.body().string();
    }


    private static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMddHHmmss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return sdf.format(date);
    }
    private static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    private static int getYear(){
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        int res = Integer.valueOf(year);
        return 40+(res-2020);
    }
    private static String getTerm(){
        Calendar date = Calendar.getInstance();
        return date.get(Calendar.MONTH)>8?"2":"1";
    }
}
