package com.psychscribe.prelogin.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentPlanPreviewBinding;
import com.psychscribe.prelogin.PlanActivity;

/**
 * Created by ubuntu on 21/7/16.
 */
public class PlanPreviewFragment extends BaseFragment {

    private FragmentPlanPreviewBinding binding;
    private final static String Position = "pos";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan_preview, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline(getContext()))
                    ((PlanActivity)getActivity()).onPurchaseItemClick();
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.error_internet_connect));
            }
        });
    }

}
