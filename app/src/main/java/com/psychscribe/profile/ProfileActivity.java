package com.psychscribe.profile;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivityWithNavigationDrawer;
import com.psychscribe.patients.AddPatientActivity;
import com.psychscribe.patients.fragment.PatientListFragment;
import com.psychscribe.profile.fragment.ProfileEditFragment;
import com.psychscribe.profile.fragment.ProfileViewFragment;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;

public class ProfileActivity extends BaseActivityWithNavigationDrawer {

    private View view;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.activity_containts, getMiddleContent());
        setupSlideWindowAnimations(Gravity.BOTTOM);
        txtToolbarTitle.setText(getString(R.string.profile));
        replaceFragment(new ProfileViewFragment(), Constants.KEY_PROFILE_VIEW_FRAGMENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                openProfileEditFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openProfileEditFragment() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        txtToolbarTitle.setText(getString(R.string.edit_profile));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackHandle();
            }
        });
        addFragmentWithSlideAnim(new ProfileEditFragment(), Constants.KEY_PROFILE_EDIT_FRAGMENT);
    }

    private void onBackHandle() {
        syncToolbarState();
        txtToolbarTitle.setText(getString(R.string.profile));
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackHandle();
    }
}
