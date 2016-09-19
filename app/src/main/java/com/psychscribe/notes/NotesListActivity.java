package com.psychscribe.notes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.psychscribe.BR;
import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityNotesListBinding;
import com.psychscribe.notes.fragment.AddNotesListFragment;
import com.psychscribe.notes.fragment.NotesListFragment;
import com.psychscribe.notes.model.NotesListModel;
import com.psychscribe.patients.AddPatientActivity;
import com.psychscribe.patients.adapter.CollateralRecyclerAdapter;
import com.psychscribe.patients.model.PatientModel;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.binding.RecyclerBindingAdapter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends BaseActivity {

    public ActivityNotesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_list);
        setupSlideWindowAnimations(Gravity.BOTTOM, Gravity.BOTTOM);
        init();
        replaceFragment(new NotesListFragment(), "");
    }

    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.notes));
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back);
        setTitle("");
        binding.includeToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressHandle();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onBackPressHandle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_add_notes).setVisible(true);
        menu.findItem(R.id.menu_search).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_notes:
                binding.includeToolbar.toolbarTitle.setText(getString(R.string.add_notes));
                addFragmentWithSlideAnim(new AddNotesListFragment(), Constants.KEY_ADD_NOTES_FRAGMENT);
                return true;

            case R.id.menu_done_mental_notes:

                return true;
            case R.id.menu_done_play_assessment_notes:

                return true;
            case R.id.menu_done_progress_notes:

                return true;
            case R.id.menu_done_psychotherapy_notes:

                return true;
            case R.id.menu_done_sandtray_notes:

                return true;
            case R.id.menu_done_sope_notes:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressHandle() {
        FragmentManager manager = getSupportFragmentManager();
        int backStackEntryCount = manager.getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            String frname = manager.getBackStackEntryAt(backStackEntryCount-1).getName();
            if(frname.equals(Constants.KEY_ADD_NOTES_FRAGMENT)){
                binding.includeToolbar.toolbarTitle.setText(getString(R.string.notes));
            }else if(frname.equals(Constants.KEY_INITIAL_INTAKE_FRAGMENT)){
                binding.includeToolbar.toolbarTitle.setText(getString(R.string.add_notes));
            }
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
