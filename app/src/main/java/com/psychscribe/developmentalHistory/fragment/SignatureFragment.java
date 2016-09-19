package com.psychscribe.developmentalHistory.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentChildCareHistoryBinding;
import com.psychscribe.databinding.FragmentSignatureBinding;
import com.psychscribe.utiz.customvViews.SignatureView;

/**
 * Created by ubuntu on 2/8/16.
 */
public class SignatureFragment extends BaseFragment {

    private FragmentSignatureBinding binding;
    private SignatureView mSignature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signature, null, false);
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
        menu.findItem(R.id.menu_done).setVisible(true);
    }
}
