package com.psychscribe.profile.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentProfileViewBinding;
import com.psychscribe.utiz.AnimationUtils;
import com.psychscribe.utiz.AppUtils;

/**
 * Created by ubuntu on 28/7/16.
 */
public class ProfileViewFragment extends BaseFragment {

    FragmentProfileViewBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_view, null, false);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_edit).setVisible(true);
        menu.findItem(R.id.menu_done).setVisible(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    AnimationUtils.collapse(binding.linSign);
                    v.setTag("0");
                    AnimationUtils.rotate0to180(binding.imgExpandSign);
                }else{
                    AnimationUtils.expand(binding.linSign);
                    v.setTag("1");
                    AnimationUtils.rotate180to360(binding.imgExpandSign);
                }
            }
        });
    }
}
