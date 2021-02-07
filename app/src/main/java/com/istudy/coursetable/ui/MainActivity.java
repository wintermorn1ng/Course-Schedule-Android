package com.istudy.coursetable.ui;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.istudy.coursetable.R;
import com.istudy.coursetable.ui.fragment.HomeFlag;
import com.istudy.coursetable.ui.fragment.SettingFrag;
import com.istudy.coursetable.ui.fragment.TableFrag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TableFrag fragment1;
    private Fragment fragment2;
    private Fragment fragment3;


    @BindView(R.id.expandable_bottom_bar)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActivity();
    }
    private void initActivity(){
        selectTab(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home_it){
                    selectTab(1);
                    return true;
                }
                else if(item.getItemId()==R.id.settings_it){
                    selectTab(2);
                    return true;
                }
                else if(item.getItemId()==R.id.bookmarks_it){
                    selectTab(3);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"activity result");
        switch (requestCode){
        }
    }

    public void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void selectTab(int i){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        if(i==1){
            if(fragment1==null){
                fragment1 = new TableFrag();
                transaction.add(R.id.main_content,fragment1);
            }
            else{
                transaction.show(fragment1);
            }
        }
        else if(i==2){
            if(fragment2==null){
                fragment2 = new HomeFlag();
                transaction.add(R.id.main_content,fragment2);
            }
            else{
                transaction.show(fragment2);
            }
        }
        else if(i==3){
            if(fragment3==null){
                fragment3 = new SettingFrag();
                transaction.add(R.id.main_content,fragment3);
            }
            else{
                transaction.show(fragment3);
            }
        }
        transaction.commit();
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }
    }
}