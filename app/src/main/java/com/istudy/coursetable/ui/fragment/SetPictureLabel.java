package com.istudy.coursetable.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.istudy.coursetable.R;

public class SetPictureLabel extends DialogFragment {

    private TextInputEditText label_et;
    private Button okButton;
    private View.OnClickListener onClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_picture_label, container, false);
        label_et = root.findViewById(R.id.label);
        okButton = root.findViewById(R.id.login_btn);
        okButton.setOnClickListener(onClickListener);

        Button cancelButton = root.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(v->dismiss());
        return root;
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public String getLabel(){
        if(label_et.getText()==null)return "";
        return label_et.getText().toString();
    }
}