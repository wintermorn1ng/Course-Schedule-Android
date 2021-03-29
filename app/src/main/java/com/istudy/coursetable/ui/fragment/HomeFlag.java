package com.istudy.coursetable.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Picture;
import com.istudy.coursetable.bean.PictureList;
import com.istudy.coursetable.db.SharedPreferencesHelper;
import com.istudy.coursetable.ui.MainActivity;
import com.istudy.coursetable.ui.adapter.PictureViewAdapter;
import com.istudy.coursetable.util.GsonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static android.app.Activity.RESULT_OK;


/**
 *
 * 图片标签，按标签分类
 * 点击图片看大图
 */

public class HomeFlag extends Fragment {
    private final int TAKE_PHOTO = 1;//拍照操作
    private final int CROP_PHOTO = 2;//切图操作
    private final int REQ_CODE = 267;       //我的拍照
    /*
     *  拍照所得到的图像的保存路径
     */
    private Uri imageUri;

    /*
     *  当前用户拍照或者从相册选择的照片的文件名
     */
    private String fileName;


    //private TableRow.LayoutParams photoParams;
    //private TableRow.LayoutParams uploadStateMsgParam;
    private RecyclerView tableLayout;
   // private ImageView mPicture;
    private Uri mImageUri;
    private static final String FILE_PROVIDER_AUTHORITY =  "com.istudy.coursetable.fileprovider";

    private PictureList pictureList;
    private PictureViewAdapter pictureViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA);
        View view = inflater.inflate(R.layout.flag_home,container,false);
        //view.findViewById(R.id.TABLE_LAYOUT_TAKE_PHOTO_LIST);
       // mPicture = view.findViewById(R.id.IMAGE_VIEW);

        Button button = view.findViewById(R.id.BUTTON_TAKE_PHOTO);
        tableLayout = view.findViewById(R.id.TABLE_LAYOUT_TAKE_PHOTO_LIST);
        button.setOnClickListener(e->{
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermissions();
                //权限还没有授予，需要在这里写申请权限的代码
            }else {
                takePhoto();
                //权限已经被授予，在这里直接写要执行的相应方法即可
            }
        });
        //this.initLayoutParams();
        initRecycleView();
        try {
            getPictureList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 请求拍照和存储权限   应该先验证的
     */
    private void requestCameraPermissions(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                },
                1);
    }

    /**
     * 拍照
     */
    /*
    private void takePhoto(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
        if(takePhotoIntent.resolveActivity(getActivity().getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            startActivityForResult(takePhotoIntent,REQ_CODE);//启动相机
        }
    }*/
    private void takePhoto(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
        if(takePhotoIntent.resolveActivity(getActivity().getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            File imageFile = createImageFile();//创建用来保存照片的文件
            if(imageFile!=null){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    /*7.0以上要通过FileProvider将File转化为Uri*/
                    mImageUri = FileProvider.getUriForFile(getContext(),FILE_PROVIDER_AUTHORITY,imageFile);
                }else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    mImageUri = Uri.fromFile(imageFile);
                }
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);//将用于输出的文件Uri传递给相机
                startActivityForResult(takePhotoIntent, REQ_CODE);//打开相机
            }
        }
    }
    /**
     * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
     * @return 创建的图片文件
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis()));
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== REQ_CODE &&resultCode==RESULT_OK){
            try {
                /*如果拍照成功，将Uri用BitmapFactory的decodeStream方法转为Bitmap*/
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(mImageUri));
               // mPicture.setImageBitmap(bitmap);//显示到ImageView上
               // galleryAddPic(mImageUri);
                //Log.d("DEBUG",mImageUri.toString());
                SetPictureLabel setPictureLabel = new SetPictureLabel();
                setPictureLabel.show(getParentFragmentManager(),"tag2");
                setPictureLabel.setOnClickListener(v -> {
                    Picture picture = new Picture(mImageUri.toString(),setPictureLabel.getLabel());
                    addPictureToList(bitmap,picture);
                    setPictureLabel.dismiss();
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void addPictureToList(Bitmap bitmap,Picture picture){
        pictureList.add(picture);
        SharedPreferencesHelper.getInstance().setPictureList(GsonUtil.gson.toJson(pictureList));
        addPhotoToActivity(bitmap,picture.getLabel(),picture.getUri());
    }

    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void getPictureList() throws FileNotFoundException {
        String str = SharedPreferencesHelper.getInstance().getPictureList();
        if(str==""){
            pictureList = new PictureList();
            return;
        }
        pictureList = GsonUtil.gson.fromJson(str,PictureList.class);
        for(int i=0;i<pictureList.length();i++){
            Picture picture = pictureList.get(i);
            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse(picture.getUri())));
            addPhotoToActivity(bitmap,picture.getLabel(),picture.getUri());
        }
    }

    private void showPicture(Uri uri) throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View imgEntryView = inflater.inflate(R.layout.fragment_photo_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        img.setImageBitmap(bitmap);
        imgEntryView.setOnClickListener(paramView -> dialog.cancel());

        dialog.setView(imgEntryView); // 自定义dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();


    }



    private void initRecycleView(){
        tableLayout.setLayoutManager(new GridLayoutManager(getActivity(),4));
        pictureViewAdapter = new PictureViewAdapter();
        tableLayout.setAdapter(pictureViewAdapter);
        tableLayout.setItemAnimator(new DefaultItemAnimator());
    }



    /**
     * 当用户点击按钮时，打开摄像头进行拍照
     * @param
     */
    public void takePohto(){
        /*
         *  用时间戳的方式来命名图片文件，这样可以避免文件名称重复
         */
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        this.fileName = "easyasset"+format.format(date);

        /*
         *  创建一个File对象，用于存放拍照所得到的照片
         */
        File path =new File(Environment.getExternalStorageDirectory(),"pic");
        if(!path.exists()){
            path.mkdir();
        }
        File outputImage = new File(path,this.fileName+".jpg");
        /*
         *  以防万一，看一下这个文件是不是存在，如果存在的话，先删除掉
         */
        if(outputImage.exists()){
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         *  将File对象转换为Uri对象，以便拍照后保存
         */
        //this.imageUri = Uri.fromFile(outputImage);
        this.imageUri = FileProvider.getUriForFile(
                getContext(),
                 getActivity().getPackageName()+".fileprovider",
                outputImage);
        /*
         *  启动系统的照相Intent
         */
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //Android系统自带的照相intent
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri); //指定图片输出地址
        startActivityForResult(intent,this.TAKE_PHOTO); //以forResult模式启动照相intent
    }


    private void cropPhoto(Uri imageUri){
        /*
         *  准备打开系统自带的裁剪图片的intent
         */
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("scale", true);

        /*
         *  设置裁剪区域的宽高比例
         */
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        /*
         *  设置裁剪区域的宽度和高度
         */
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);

        /*
         *  指定裁剪完成以后的图片所保存的位置
         */
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri);
        Toast.makeText(getContext(), "剪裁图片", Toast.LENGTH_SHORT).show();

        /*
         *  以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
         */
        /*
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(this.imageUri);
        this.sendBroadcast(intentBc);
        */
        /*
         *  以forResult模式启动系统自带的裁剪图片的intent
         */
        startActivityForResult(intent, CROP_PHOTO); //设置裁剪参数显示图片至ImageView
    }

    /**
     * 因为启动拍照intent的时候是使用的forResult模式，因此需要onActivityResult方法来接受回调参数
     * @param requestCode
     * @param resultCode
     * @param data
     */


    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode != RESULT_OK) {
            Toast.makeText(getContext(), "获取图片出现错误", Toast.LENGTH_SHORT).show();
        }
        else{
            switch(requestCode) {

                 //  case TAKE_PHOTO 代表从拍摄照片的intent返回之后
                 //  完成拍摄照片之后，立刻打开系统自带的裁剪图片的intent

                case TAKE_PHOTO:
                    this.cropPhoto(this.imageUri);
                    break;


                 // case CROP_PHOTO 代表从裁剪照片的intent返回之后
                 // 完成裁剪照片后，就要将图片生成bitmap对象，然后显示在界面上面了

                case CROP_PHOTO:
                    try {
                        //
                         //  将图片转换成Bitmap对象

                        //Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(this.imageUri));
                        //Toast.makeText(getContext(), this.imageUri.toString(), Toast.LENGTH_SHORT).show();

                        //
                        //  在界面上显示图片

                        //this.addPhotoToActivity(bitmap);
                    } catch(FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;


                default:
                    break;
            }
        }
    }
    */

    /**
     * 初始化一些界面组件的样式
     */
    /*
    private void initLayoutParams() {
         *  拍照所得到的图片被放置在界面上时，其在TableRow所占的宽度占比
        this.photoParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                268,
                0.1f
        );

         *  照片上传状态的文字信息被放置在界面上时，其在TableRow所占的宽度占比
        this.uploadStateMsgParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                268,
                0.9f
        );
    }
*/
    /**
     * 将拍照和裁剪后所得到的照片，罗列在界面上
     */
    private  void addPhotoToActivity(Bitmap bitMap,String label,String url){
        /*
         *  首先获取到用来显示照片的容器
         *  该容易是一个TableLayout
         */

        /*
         *  创建一个TableRow对象
         *  每一行TableRow对象都用来存放一张照片，以及该照片的上传情况信息
         *  将这个TableRow放入TableLayout中
         */
        //TableRow tableRow = new TableRow(getContext());
        //tableRow.setPadding(0,0,0,8);//设置每一行的下间距
        //tableLayout.addView(tableRow);

        /*
         *  创建一个ImageView对象
         *  将这个对象放入TableRow中
         *  并在这个对象上显示刚刚拍照所得到的照片
         */
        //ImageView imageView = new ImageView(getContext());
        //imageView.setLayoutParams(this.photoParams);
        //imageView.setImageBitmap(bitMap);
       // tableRow.addView(imageView);

        /*
         *  创建一个TextView对象
         *  为这个对象设置一段“图片正在上传”的提示文字
         *  并将这个TextView对象放入TableRow中
         */
        /*
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(this.uploadStateMsgParam);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText(label);
        textView.setTextColor(ContextCompat.getColor(getContext(),R.color.blue_500));
        tableRow.addView(textView);
        tableRow.setOnClickListener(v->{
            try {
                showPicture(Uri.parse(url));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        */
         pictureViewAdapter.addItem(label, bitMap, new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     showPicture(Uri.parse(url));
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 }
             }
         });
    }

}
