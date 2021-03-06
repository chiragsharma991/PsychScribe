package com.psychscribe.developmentalHistory.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentChildCareHistoryBinding;
import com.psychscribe.databinding.FragmentDevelopmentMilestonesBinding;

/**
 * Created by ubuntu on 30/7/16.
 */
public class ChildCareHistoryFragment extends BaseFragment {

    private FragmentChildCareHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_child_care_history, null, false);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_next).setVisible(true);
    }
}
