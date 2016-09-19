package com.psychscribe.patients;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityPatientDetailBinding;
import com.psychscribe.developmentalHistory.DevelopmentHistoryActivity;
import com.psychscribe.notes.NotesListActivity;
import com.psychscribe.patients.model.PatientModel;
import com.psychscribe.rx.RXMainActivity;
import com.psychscribe.treatmentPlan.TreatmentPlanActivity;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;

public class PatientDetailActivity extends BaseActivity {

    ActivityPatientDetailBinding binding;
    private PatientModel.PatientData patientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_detail);
        setupSlideWindowAnimations(Gravity.BOTTOM, Gravity.BOTTOM);
        patientData = (PatientModel.PatientData) getIntent().getSerializableExtra(Constants.ExtraPatient);
        initToolbar();
        setData();
    }

    private void setData() {
        binding.toolbarTitle.setText(patientData.getFirstName());
        binding.txtDate.setText(AppUtils.dateConvert("yyyy-MM-dd", "dd MMM yyyy", patientData.getDob()));
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        setTitle("");
        /**
         * For image draw over status bar
         */
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            binding.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void onClickDevelopmentHistory(View view){
        moveActivity(new Intent(this, DevelopmentHistoryActivity.class), this);
    }

    public void onClickPatientInfo(View view){
        moveActivity(new Intent(this, AddPatientActivity.class), this);
    }

    public void onClickNotes(View view){
        moveActivity(new Intent(this, NotesListActivity.class), this);
    }

    public void onClickTreatmentPlan(View view){
        moveActivity(new Intent(this, TreatmentPlanActivity.class), this);
    }

    public void onClickRx(View view){
        moveActivity(new Intent(this, RXMainActivity.class), this);
    }
}
