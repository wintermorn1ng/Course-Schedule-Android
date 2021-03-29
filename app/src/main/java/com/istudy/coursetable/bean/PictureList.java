package com.istudy.coursetable.bean;

import java.util.ArrayList;

public class PictureList {
    private ArrayList<Picture> list;

    public PictureList() {
        list = new ArrayList<>();
    }
    public Picture get(int index){
        return list.get(index);
    }
    public void add(Picture picture){
        list.add(picture);
    }
    public int length(){
        return list.size();
    }
}
