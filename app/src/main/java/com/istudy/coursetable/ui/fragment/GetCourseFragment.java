package com.istudy.coursetable.ui.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.istudy.coursetable.R;
import com.istudy.coursetable.util.GetInfoUtil;

public final class GetCourseFragment extends DialogFragment {
    private View.OnClickListener onClickListener;
    private ImageView imageView;
    private Button okButton;
    private TextInputEditText username_et;
    private TextInputEditText password_et;
    private TextInputEditText captcha_et;
    private TextInputLayout username_ty;
    private TextInputLayout password_ty;
    private TextInputLayout captcha_ty;
    private ProgressDialog progressDialog;
    public TableFrag parent;

    public GetCourseFragment(TableFrag parent) {
        super();
        this.parent = parent;
    }

    //TODO 可能会造成内存泄漏
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_course_getter,container);
        imageView = root.findViewById(R.id.code_img);
        okButton = root.findViewById(R.id.login_btn);
        username_et = root.findViewById(R.id.username_et);
        username_ty = root.findViewById(R.id.username_ty);
        password_et = root.findViewById(R.id.password_et);
        password_ty = root.findViewById(R.id.password_ty);
        captcha_et = root.findViewById(R.id.captcha_et);
        captcha_ty = root.findViewById(R.id.captcha_ty);

        okButton.setOnClickListener(onClickListener);

        Button cancelButton = root.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(v->dismiss());

        imageView.setOnClickListener(v->refreshCaptcha());
        refreshCaptcha();
        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        progressDialog.dismiss();
    }

    public void setOkButton(View.OnClickListener v){
        onClickListener = v;
    }

    public void refreshCaptcha(){
        GetInfoUtil.getInstance().getCaptcha(this);
    }

    public void updateImg(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void showMessage(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }

    public void showLoading(String message){
        progressDialog = ProgressDialog.show(mContext,"请稍候",message);
    }
    public void hideLoading(){
        progressDialog.dismiss();
    }
    public void setInputError(){
        username_ty.setError("请检查学号");
        password_ty.setError("请检查密码");
    }
    public void setCaptchaError(){
        captcha_ty.setError("请检查验证码");
    }
    private void initError(){
        username_ty.setError(null);
        password_ty.setError(null);
        captcha_ty.setError(null);
    }

    public String getUsername(){
        if(username_et.getText()==null)return null;
        return username_et.getText().toString().trim();
    }
    public String getPassword(){
        if(password_et.getText()==null)return null;
        return password_et.getText().toString().trim();
    }
    public String getCaptcha(){
        if(captcha_et.getText()==null)return null;
        return captcha_et.getText().toString().trim();
    }


}
