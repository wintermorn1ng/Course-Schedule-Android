package com.istudy.coursetable.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.istudy.coursetable.ui.CourseGetterActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil extends OkhttpUtil {
    private final String TAG = "HTTPUTIL";
    private volatile static HttpUtil instance;
    private HttpUtil(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    public static HttpUtil getInstance(){
        if(instance==null){
            synchronized (HttpUtil.class){
                if(instance==null){
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }

    public void getCaptcha(CourseGetterActivity context){
        Observable.just(URLManager.URL_getCaptcha)
           .map(new Function<Object, Bitmap>() {
               @Override
               public Bitmap apply(Object o) throws Throwable {
                   try {
                       return getCaptcha();
                   }
                   catch (SocketTimeoutException e){
                       return null;
                   }
               }
           })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Observer<Bitmap>() {
               @Override
               public void onSubscribe(@NonNull Disposable d) {
                   context.showLoading("正在加载验证码");
               }

               @Override
               public void onNext(@NonNull Bitmap bitmap) {
                       context.updateImg(bitmap);
               }

               @Override
               public void onError(@NonNull Throwable e) {
                   Log.d(TAG,"network err");
                   e.printStackTrace();
                   context.hideLoading();
                   context.showMessage("网络错误！");
               }

               @Override
               public void onComplete() {
                   context.hideLoading();
               }
           });
    }

    public void getCourseTable(CourseGetterActivity context, String username, String password, String captcha){
        Observable.just("1")
            .map(new Function<Object, Boolean>() {
                @Override
                public Boolean apply(Object s) throws Throwable {
                    boolean res = checkCaptcha(captcha);
                    return res;
                }
            })
            .map(new Function<Boolean, String>() {
                @Override
                public String apply(Boolean aBoolean) throws Throwable {
                    if(!aBoolean)return "__false__";
                    return login(username,password,captcha);
                }
            })
            .map(new Function<String, String>() {
                @Override
                public String apply(String s) throws Throwable {
                    if(s.equals("__false__"))return s;
                    return getClassTable();
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    context.showLoading("正在获取课表");
                }

                @Override
                public void onNext(@NonNull String s) {
                    if(s.equals("__false__")){
                        context.showMessage("请求失败，请检查学号，密码，验证码后重试");
                        context.hideLoading();
                        context.refreshCaptcha();
                    }
                    else {
                        Logger.d(s);
                        context.processData(s);
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d(TAG,"network err");
                    e.printStackTrace();
                    context.hideLoading();
                }

                @Override
                public void onComplete() {
                    context.hideLoading();
                }
            });
    }



    private Bitmap getCaptcha() throws IOException {
        String target = URLManager.URL_getCaptcha+Math.random();
        Request request = new Request.Builder().url(target).header("User-Agent",USERAGENT).build();
        Response response = okHttpClient.newCall(request).execute();
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

    private boolean checkCaptcha(String captchaCode) throws IOException{
        RequestBody requestBody = new FormBody.Builder().add("captchaCode",captchaCode).build();
        Request request = new Request.Builder().post(requestBody).url(URLManager.URL_checkCaptcha)
                .header("User-Agent",USERAGENT)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string().equals("true");
    }

    private String login(String username,String password,String captchaCode) throws IOException{
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
        return "__false__";
    }

    private String getClassTable() throws IOException {
        String url = URLManager.URL_gotoClassTableLocation;
        url = url + getTime() + getRandomString(6);
        Request request = new Request.Builder().url(url)
                .header("User-Agent",USERAGENT)
                .build();
        Response response = okHttpClient.newCall(request).execute();
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
}
