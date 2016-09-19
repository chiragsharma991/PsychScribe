package com.psychscribe.patients;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.JsonObject;
import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityAddPatientBinding;
import com.psychscribe.patients.fragment.CollateralFragment;
import com.psychscribe.patients.fragment.EmergencyFragment;
import com.psychscribe.patients.fragment.InsuranceFragment;
import com.psychscribe.patients.fragment.PersonalFragment;
import com.psychscribe.utiz.AnimationUtils;
import com.psychscribe.utiz.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddPatientActivity extends BaseActivity {

    private ActivityAddPatientBinding binding;
    private PersonalFragment personalFragment;
    private InsuranceFragment insuranceFragment;
    private CollateralFragment collateralFragment;
    private EmergencyFragment emergencyFragment;
    private AnimationUtils animationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_patient);
        binding.setSelectedPosition(0);
        setupSlideWindowAnimations(Gravity.BOTTOM, Gravity.BOTTOM);
        init();
        setPagerAdapter();
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 4;
            }
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        personalFragment = new PersonalFragment();
                        return personalFragment;
                    case 1:
                        emergencyFragment = new EmergencyFragment();
                        return emergencyFragment;
                    case 2:
                        insuranceFragment = new InsuranceFragment();
                        return insuranceFragment;
                    case 3:
                        collateralFragment = new CollateralFragment();
                        return collateralFragment;
                }
                return null;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.setCurrentItem(0);
    }

    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.add_patient));
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back);
        setTitle("");
        initTabAnim();
        binding.txtDate.setText(AppUtils.dateConvert("MM-dd-yyyy", "dd-MMMM-yyyy", AppUtils.getCurrantDate()));
        binding.includeToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initTabAnim() {
        animationUtils = new AnimationUtils();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationUtils.animateIn(binding.linTab);
            }
        }, ANIM_TIME);
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
            case R.id.menu_next:
                changeFragment();
                return true;
            case R.id.menu_done:
                createPatientJason();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createPatientJason() {
        if(personalFragment != null && emergencyFragment != null && insuranceFragment != null && collateralFragment != null){
            if(personalFragment.isValidate() && emergencyFragment.isValidateInsuranceList() && insuranceFragment.isValidateInsuranceList() &&
                    collateralFragment.isValidateInsuranceList()) {
                try {
                    JSONObject mainJsonObject = personalFragment.getAllValue();
                    mainJsonObject.put("insurance", insuranceFragment.getSelectedValue());
                    mainJsonObject.put("emergencycontacts", emergencyFragment.getSelectedValue());
                    mainJsonObject.put("collaterals", collateralFragment.getSelectedValue());
                    showErrorLog(mainJsonObject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void changeFragment() {
        binding.viewPager.setCurrentItem((binding.viewPager.getCurrentItem()+1));
        switch (binding.viewPager.getCurrentItem()){
            case 0:
                onClickTab(binding.imgPerson);
                break;
            case 1:
                onClickTab(binding.imgEmergency);
                break;
            case 2:
                onClickTab(binding.imgInsurance);
                break;
            case 3:
                onClickTab(binding.imgCollateral);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickTab(View view){
        switch (view.getTag().toString()){
            case "0":
                binding.setSelectedPosition(0);
                binding.viewPager.setCurrentItem(0);
                break;
            case "1":
                binding.viewPager.setCurrentItem(1);
                binding.setSelectedPosition(1);
                break;
            case "2":
                binding.viewPager.setCurrentItem(2);
                binding.setSelectedPosition(2);
                break;
            case "3":
                binding.viewPager.setCurrentItem(3);
                binding.setSelectedPosition(3);
                break;

        }
       // binding.executePendingBindings();
    }
}
