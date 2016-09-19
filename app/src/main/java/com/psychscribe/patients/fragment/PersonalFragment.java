package com.psychscribe.patients.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentPersonalBinding;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.fragment.DatePickerFragment;
import com.psychscribe.utiz.fragment.StateDialogFragment;

import org.json.JSONObject;

/**
 * Created by ubuntu on 21/7/16.
 */
public class PersonalFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

    private FragmentPersonalBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.edtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDatePickerDialog();
            }
        });

        binding.edtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStateDialog();
            }
        });
    }

    void openStateDialog(){
        StateDialogFragment dialogFragment = new StateDialogFragment();
        dialogFragment.setTargetFragment(this, Constants.REQUEST_STATE_DIALOG);
        dialogFragment.show(getChildFragmentManager(), "State");
    }

    private void openDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setListener(this, true);
        newFragment.show(getChildFragmentManager(), "DatePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        binding.edtDateOfBirth.setText(""+(monthOfYear+1)+"-"+dayOfMonth+"-"+year);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == Constants.REQUEST_STATE_DIALOG) {
                binding.edtState.setText(data.getStringExtra(Constants.ExtraStateName));
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_done).setVisible(false);
        menu.findItem(R.id.menu_next).setVisible(true);
    }

    public boolean isValidate(){
        if(TextUtils.isEmpty(binding.edtFirstName.getText().toString().trim())){
            showSnackbar(binding.getRoot(), getString(R.string.error_enter_first_name));
            return false;
        }else if(TextUtils.isEmpty(binding.edtLastName.getText().toString().trim())){
            showSnackbar(binding.getRoot(), getString(R.string.error_enter_last_name));
            return false;
        }else if(TextUtils.isEmpty(binding.edtMiddleInitial.getText().toString().trim())){
            showSnackbar(binding.getRoot(), getString(R.string.error_enter_middle_initial));
            return false;
        }else if(TextUtils.isEmpty(binding.edtDateOfBirth.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.date_of_birth).toLowerCase()));
            return false;
        }else if(TextUtils.isEmpty(binding.edtAddress.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.address).toLowerCase()));
            return false;
        }else if(TextUtils.isEmpty(binding.edtCity.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.city).toLowerCase()));
            return false;
        }else if(TextUtils.isEmpty(binding.edtState.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.state).toLowerCase()));
            return false;
        }else if(TextUtils.isEmpty(binding.edtZip.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.zip_code).toLowerCase()));
            return false;
        } else if (TextUtils.isEmpty(binding.edtEmail.getText().toString().trim())) {
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.email).toLowerCase()));
            return false;
        } else if(!AppUtils.isValidEmail(binding.edtEmail.getText().toString())) {
            showSnackbar(binding.getRoot(), getString(R.string.error_valid_email));
            return false;
        }else if(TextUtils.isEmpty(binding.edtMobile.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.mobile).toLowerCase()));
            return false;
        } else if(TextUtils.isEmpty(binding.edtPhoneNoOne.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.phone1).toLowerCase()));
            return false;
        }else if(TextUtils.isEmpty(binding.edtPhoneNoTwo.getText().toString().trim())){
            showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.phone2).toLowerCase()));
            return false;
        }
        return true;
    }

    public JSONObject getAllValue(){
        JSONObject mainJsonObject = new JSONObject();
        try {
            mainJsonObject.put("first_name", ""+binding.edtFirstName.getText().toString());
            mainJsonObject.put("last_name", ""+binding.edtLastName.getText().toString());
            mainJsonObject.put("middle_initial", ""+binding.edtMiddleInitial.getText().toString());
            mainJsonObject.put("dob", ""+binding.edtDateOfBirth.getText().toString());
            mainJsonObject.put("address", ""+binding.edtAddress.getText().toString());
            mainJsonObject.put("city", ""+binding.edtCity.getText().toString());
            mainJsonObject.put("state", ""+binding.edtState.getText().toString());
            mainJsonObject.put("zip_code", ""+binding.edtZip.getText().toString());
            mainJsonObject.put("email", ""+binding.edtEmail.getText().toString());
            mainJsonObject.put("phone_1", ""+binding.edtPhoneNoOne.getText().toString());
            mainJsonObject.put("phone_2", ""+binding.edtPhoneNoTwo.getText().toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return mainJsonObject;
    }
}
