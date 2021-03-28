package com.istudy.coursetable.ui;

import androidx.appcompat.app.AppCompatActivity;

@Deprecated
public class CourseGetterActivity extends AppCompatActivity {
/*

    @BindView(R.id.code_img)
    ImageView imageView;
    @BindView(R.id.login_btn)
    Button button;
    @BindView(R.id.username_et)
    TextInputEditText username_et;
    @BindView(R.id.password_et)
    TextInputEditText password_et;
    @BindView(R.id.captcha_et)
    TextInputEditText captcha_et;
    @BindView(R.id.cancel_btn)Button cancel_btn;
    @BindView(R.id.username_ty)
    TextInputLayout username_ty;
    @BindView(R.id.password_ty)
    TextInputLayout password_ty;
    @BindView(R.id.captcha_ty)
    TextInputLayout captcha_ty;


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_getter);
        ButterKnife.bind(this);
        setTitle("教务在线获取课表");
        uiInit();
    }

    private void uiInit(){
        button.setOnClickListener(v->{
            initError();
            String username = username_et.getText().toString();
            String password = password_et.getText().toString();
            String captcha = captcha_et.getText().toString();
            GetInfoUtil.getInstance().getCourseTable(this,username,password,captcha);
        });
        imageView.setOnClickListener(e->{
            refreshCaptcha();
        });
        cancel_btn.setOnClickListener(e->{
            finish();
        });
        refreshCaptcha();
    }

    public void refreshCaptcha(){
        GetInfoUtil.getInstance().getCaptcha(this);
    }

    public void updateImg(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }
    public void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public static void activityStart(Activity context){
        Intent intent = new Intent(context,CourseGetterActivity.class);
        context.startActivityForResult(intent,2);
    }

    public void processData(String data){
        CourseInfos courseInfos = new CourseInfos(HTML2CourseRepUtil.parse(data));
        if(courseInfos.list==null){ //TODO 错误处理
            showMessage("解析失败，请重试");
            return ;
        }
        Intent intent = new Intent();
        intent.putExtra("course_info", GsonUtil.gson.toJson(courseInfos));
        setResult(RESULT_OK,intent);
        finish();
    }

    public void showLoading(String message){
        progressDialog = ProgressDialog.show(this,"请稍候",message);
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
*/
}