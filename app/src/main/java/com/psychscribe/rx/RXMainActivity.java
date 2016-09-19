package com.psychscribe.rx;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityNotesDetailBinding;
import com.psychscribe.rx.fragment.RxListFragment;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.treatmentPlan.fragment.TreatmentPlanFragment;
import com.psychscribe.utiz.Constants;

public class RXMainActivity extends BaseActivity {

    ActivityNotesDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_detail);
        setupSlideWindowAnimations(Gravity.BOTTOM, Gravity.BOTTOM);
        init();
        replaceFragment(new RxListFragment(), Constants.KEY_RX_LIST_FRAGMENT);
    }

    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.rx));
        binding.includeToolbar.toolbarLeftTitle.setVisibility(View.VISIBLE);
        binding.includeToolbar.toolbarLeftTitle.setText(SharedPreferenceUtil.getString(Constants.KEY_USER_NAME, ""));
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back);
        setTitle("");
        binding.includeToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_patient, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_done).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        onBackPressHandle();
    }

    public void onBackPressHandle() {
        FragmentManager manager = getSupportFragmentManager();
        int backStackEntryCount = manager.getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            String frname = manager.getBackStackEntryAt(backStackEntryCount-1).getName();
            if(frname.equals(Constants.KEY_ADD_NOTES_FRAGMENT)){
                binding.includeToolbar.toolbarTitle.setText(getString(R.string.notes));
            }
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
