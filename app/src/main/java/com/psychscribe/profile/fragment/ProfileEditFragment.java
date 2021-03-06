package com.psychscribe.profile.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentProfileEditBinding;
import com.psychscribe.databinding.FragmentProfileViewBinding;
import com.psychscribe.utiz.AnimationUtils;
import com.psychscribe.utiz.customvViews.SignatureView;

/**
 * Created by ubuntu on 28/7/16.
 */
public class ProfileEditFragment extends BaseFragment {

    FragmentProfileEditBinding binding;
    MenuItem menuDone;
    private SignatureView mSignature;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.linSignParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSignature == null) {
                    initSignatureView();
                }
            }
        });

        binding.imgExpandAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString().equals("1")){
                    AnimationUtils.collapse(binding.linAccount);
                    v.setTag("0");
                    AnimationUtils.rotate0to180(binding.imgExpandAccount);
                }else{
                    AnimationUtils.expand(binding.linAccount);
                    v.setTag("1");
                    AnimationUtils.rotate180to360(binding.imgExpandAccount);
                }
            }
        });

        binding.imgExpandSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString().equals("1")){
                    AnimationUtils.collapse(binding.linSetting);
                    v.setTag("0");
                    AnimationUtils.rotate0to180(binding.imgExpandSetting);
                }else{
                    AnimationUtils.expand(binding.linSetting);
                    v.setTag("1");
                    AnimationUtils.rotate180to360(binding.imgExpandSetting);
                }
            }
        });

        binding.imgExpandSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString().equals("1")){
                    AnimationUtils.collapse(binding.relSign);
                    v.setTag("0");
                    AnimationUtils.rotate0to180(binding.imgExpandSign);
                }else{
                    AnimationUtils.expand(binding.relSign);
                    v.setTag("1");
                    AnimationUtils.rotate180to360(binding.imgExpandSign);
                }
            }
        });
    }

    private void initSignatureView() {
        binding.linSign.setVisibility(View.VISIBLE);
        mSignature = new SignatureView(getContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        binding.linSign.addView(mSignature, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_edit).setVisible(false);
        menuDone = menu.findItem(R.id.menu_done);
        menuDone.setVisible(true);
        /*
        menuDone.setActionView(R.layout.layout_menu_done);
        RelativeLayout iv = (RelativeLayout) menuDone.getActionView().findViewById(R.id.relDone);
        AnimationUtils.animateIn(iv);
       */
    }
}
