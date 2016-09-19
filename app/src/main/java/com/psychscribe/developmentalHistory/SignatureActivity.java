package com.psychscribe.developmentalHistory;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivitySignatureBinding;
import com.psychscribe.developmentalHistory.fragment.SignatureFragment;
import com.psychscribe.utiz.Constants;

/**
 * Created by ubuntu on 2/8/16.
 */
public class SignatureActivity extends BaseActivity {

    ActivitySignatureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signature);
        init();
        replaceFragment(new SignatureFragment(), Constants.KEY_SIGNATURE_FRAGMENT);
    }


    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.signature));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
