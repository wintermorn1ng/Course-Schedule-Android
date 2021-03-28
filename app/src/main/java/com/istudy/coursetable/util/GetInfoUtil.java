package com.istudy.coursetable.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.istudy.coursetable.ui.CourseGetterActivity;
import com.istudy.coursetable.ui.fragment.GetCourseFragment;
import com.orhanobut.logger.Logger;

import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetInfoUtil extends HttpUtil{
    private GetInfoUtil(){}
    private volatile static GetInfoUtil instance;
    public static GetInfoUtil getInstance(){
        if(instance==null){
            synchronized (GetInfoUtil.class){
                if(instance==null){
                    instance = new GetInfoUtil();
                }
            }
        }
        return instance;
    }
    public void getCaptcha(GetCourseFragment context){
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

    public void getCourseTable(GetCourseFragment context, String username, String password, String captcha){
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
                        if(s.equals("__false__")||s.equals("login__false__"))return s;
                        return getClassTable();
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Throwable {
                        if(s.equals("__false__")||s.equals("login__false__"))return s;
                        return getWeek()+"$$$$$$$$$$"+s;
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
                            context.setCaptchaError();
                            context.hideLoading();
                            context.refreshCaptcha();
                        }
                        else if(s.equals("login__false__")){
                            context.showMessage("请求失败，请检查学号，密码，验证码后重试");
                            context.setInputError();
                            context.hideLoading();
                            context.refreshCaptcha();
                        }
                        else {
                            //Log.d("GetInfoUtil",s);
                            context.parent.processData(s);
                            context.dismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        context.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        context.hideLoading();
                    }
                });
    }
}
