package com.psychscribe.treatmentPlan;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityNotesDetailBinding;
import com.psychscribe.notes.fragment.NotesViewFragment;
import com.psychscribe.treatmentPlan.fragment.TreatmentPlanFragment;

public class TreatmentPlanActivity extends BaseActivity {

    ActivityNotesDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_detail);
        setupSlideWindowAnimations(Gravity.BOTTOM, Gravity.BOTTOM);
        init();
        replaceFragment(new TreatmentPlanFragment(), "");
    }

    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText("Treatment Plan");
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
}
