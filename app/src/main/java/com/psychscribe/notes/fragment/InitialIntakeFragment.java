package com.psychscribe.notes.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentInitialIntakeBinding;
import com.psychscribe.utiz.AnimationUtils;

/**
 * Created by ubuntu on 3/8/16.
 */
public class InitialIntakeFragment extends BaseFragment {

    FragmentInitialIntakeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_initial_intake, null, false);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_done_initial_notes).setVisible(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imgPresentingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString().equals("0")) {
                    AnimationUtils.delayTransaction(binding.relPresenting);
                    binding.txtPresentingInfo.setVisibility(View.VISIBLE);
                    binding.imgPresentingInfo.setImageResource(R.drawable.ic_info_active);
                    v.setTag("1");
                }else{
                    AnimationUtils.delayTransaction(binding.relPresenting);
                    binding.txtPresentingInfo.setVisibility(View.GONE);
                    binding.imgPresentingInfo.setImageResource(R.drawable.ic_info_unactive);
                    v.setTag("0");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done_initial_notes:


                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
