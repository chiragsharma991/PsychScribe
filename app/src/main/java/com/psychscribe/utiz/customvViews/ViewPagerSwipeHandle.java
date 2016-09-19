package com.psychscribe.utiz.customvViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.psychscribe.R;


public class ViewPagerSwipeHandle extends ViewPager {

    private boolean swipeable;

    public ViewPagerSwipeHandle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerSwipeHandle);
        try {
            swipeable = a.getBoolean(R.styleable.ViewPagerSwipeHandle_swipeable, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable ? super.onTouchEvent(event) : false;
    }
}