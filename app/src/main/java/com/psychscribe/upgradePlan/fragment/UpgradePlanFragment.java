package com.psychscribe.upgradePlan.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.DialogForgetpassBinding;
import com.psychscribe.databinding.FragmentUpgradePlanBinding;
import com.psychscribe.prelogin.fragment.PlanPreviewFragment;
import com.psychscribe.upgradePlan.UpgradePlanActivity;
import com.psychscribe.utiz.ZoomOutPageTransformer;

/**
 * Created by ubuntu on 30/7/16.
 */
public class UpgradePlanFragment extends BaseFragment {

    FragmentUpgradePlanBinding binding;
    private final static String Position = "pos";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upgrade_plan, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPagerAdapter();
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UpgradePlanActivity)getActivity()).openNavigationDrawer();
            }
        });
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }
            @Override
            public Fragment getItem(int position) {
                PlanPreviewFragment fragment = new PlanPreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Position, position);
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        binding.indicator.setViewPager(binding.viewPager);
        binding.viewPager.setCurrentItem(0);
    }
}
