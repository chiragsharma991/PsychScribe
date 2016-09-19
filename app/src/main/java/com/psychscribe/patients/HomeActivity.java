package com.psychscribe.patients;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivityWithNavigationDrawer;
import com.psychscribe.patients.fragment.PatientListFragment;
import com.psychscribe.utiz.Constants;

import java.lang.reflect.Field;

public class HomeActivity extends BaseActivityWithNavigationDrawer {

    private View view;
    private LayoutInflater inflater;
    private SearchView.OnQueryTextListener queryTextListener;
    private PatientListFragment patientListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.activity_containts, getMiddleContent());
        setupWindowAnimations(Gravity.RIGHT);
        patientListFragment = new PatientListFragment();
        replaceFragment(patientListFragment, Constants.KEY_PATIENT_FRAGMENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (TextUtils.isEmpty(newText)){
                        searchPatient("");
                    }else{
                        searchPatient(newText);
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.e("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void searchPatient(String newText) {
        patientListFragment.onSearch(newText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_patient:
                moveActivity(new Intent(this, AddPatientActivity.class), HomeActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
