package com.psychscribe.upgradePlan;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivityWithNavigationDrawer;
import com.psychscribe.profile.fragment.ProfileViewFragment;
import com.psychscribe.upgradePlan.fragment.UpgradePlanFragment;
import com.psychscribe.utiz.Constants;

public class UpgradePlanActivity extends BaseActivityWithNavigationDrawer {

    private View view;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.activity_containts, getMiddleContent()); setupSlideWindowAnimations(Gravity.BOTTOM);
        mToolbar.setVisibility(View.GONE);
        replaceFragment(new UpgradePlanFragment(), Constants.KEY_UPGRADE_PLAN_FRAGMENT);
    }

    public void openNavigationDrawer() {
        openCloseDrawer();
    }
}
