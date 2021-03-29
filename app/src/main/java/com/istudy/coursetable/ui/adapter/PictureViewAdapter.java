package com.istudy.coursetable.ui.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.istudy.coursetable.R;
import com.istudy.coursetable.bean.Picture;

import java.util.ArrayList;

public class PictureViewAdapter extends RecyclerView.Adapter<PictureViewAdapter.ViewHolder> {
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private ArrayList<View.OnClickListener> listeners = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_view,parent,false);
        PictureViewAdapter.ViewHolder holder = new PictureViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String label = list.get(position);
        if(label!=null){
            holder.label.setText(label);
            holder.imageView.setImageBitmap(bitmaps.get(position));
            holder.imageView.setOnClickListener(listeners.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView label;
        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.img_view);
            label = view.findViewById(R.id.text_label);
        }
    }

    public void addItem(String label, Bitmap bitmap, View.OnClickListener listener){
        list.add(label);
        bitmaps.add(bitmap);
        listeners.add(listener);
        notifyItemChanged(list.size());
    }

}
