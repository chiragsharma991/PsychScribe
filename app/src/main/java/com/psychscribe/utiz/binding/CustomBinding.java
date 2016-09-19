package com.psychscribe.utiz.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.psychscribe.R;
import com.psychscribe.utiz.CircleTransform;
import com.psychscribe.utiz.font.FontCache;

import java.util.AbstractList;

/**
 * Created by ubuntu on 19/7/16.
 */
public class CustomBinding {

    @BindingAdapter({"imageUrl"})
    public static void setImage(ImageView imageView, Drawable url){
        Glide.with(imageView.getContext()).load(R.drawable.ic_dummmy).transform(new CircleTransform(imageView.getContext())).into(imageView);
    }

    @BindingAdapter({"imageResource"})
    public static void setImageResource(ImageView imageView, int drawable){
        imageView.setImageResource(drawable);
    }

    @BindingAdapter({"font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontCache.getInstance().get(fontName));
    }

    @BindingAdapter({"items","layout","model","listener"})
    public static <T> void recycler(RecyclerView rv, AbstractList<T> items, int layout, int model, final RecyclerBindingAdapter.OnItemClickListener listener) {
        Log.e("TAG", "recycler: " + items.size());
        RecyclerBindingAdapter adapter = new RecyclerBindingAdapter<>(layout, model, items);
        rv.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(rv.getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                listener.onItemClick(position,item);
            }
        });
    }



}
