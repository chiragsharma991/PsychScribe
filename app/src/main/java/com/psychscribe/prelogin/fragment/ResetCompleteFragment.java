package com.psychscribe.prelogin.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentPlanPreviewBinding;
import com.psychscribe.databinding.FragmentResetCompleteBinding;
import com.psychscribe.prelogin.PasswordResetActivity;

/**
 * Created by ubuntu on 8/8/16.
 */
public class ResetCompleteFragment extends BaseFragment {

    private FragmentResetCompleteBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_complete, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.txtLogin.setPaintFlags(binding.txtLogin.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        binding.linLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PasswordResetActivity)getActivity()).moveToLogin();
            }
        });
    }
}
