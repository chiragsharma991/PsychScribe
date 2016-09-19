package com.psychscribe.notes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityNotesDetailBinding;
import com.psychscribe.notes.fragment.NotesViewFragment;

/**
 * Created by ubuntu on 3/8/16.
 */
public class NotesDetailActivity extends BaseActivity {

    ActivityNotesDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_detail);
        setupSlideWindowAnimations(Gravity.TOP, Gravity.BOTTOM);
        init();
        replaceFragment(new NotesViewFragment(), "");
    }

    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.notes));
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
    public void onBackPressed() {
        super.onBackPressed();

    }
}
