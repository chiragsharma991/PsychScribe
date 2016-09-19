package com.psychscribe.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.psychscribe.R;

/**
 * Created by ubuntu on 19/4/16.
 */
public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.ViewHolder> {

    private int mSize = 5;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtNav;
        ImageView mImgNav;
        public ViewHolder(View v) {
            super(v);
            mTxtNav = (TextView) v.findViewById(R.id.text_nav);
            mImgNav = (ImageView) v.findViewById(R.id.img_nav);
        }
    }

    public NavigationMenuAdapter(Context context) {
    }

    @Override
    public NavigationMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_navigation_menu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position){
            case 0:
                holder.mImgNav.setImageResource(R.drawable.ic_navigation_patients);
                holder.mTxtNav.setText("Patients");
                break;
            case 1:
                holder.mImgNav.setImageResource(R.drawable.ic_navigation_profile);
                holder.mTxtNav.setText("Profile");
                break;
            case 2:
                holder.mImgNav.setImageResource(R.drawable.ic_navigation_upgrade);
                holder.mTxtNav.setText("Upgrade Plan");
                break;
            case 3:
                holder.mImgNav.setImageResource(R.drawable.ic_navigation_help);
                holder.mTxtNav.setText("Help");
                break;
            case 4:
                holder.mImgNav.setImageResource(R.drawable.ic_navigation_logout);
                holder.mTxtNav.setText("Logout");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

}
