package com.psychscribe.prelogin.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.DialogForgetpassBinding;
import com.psychscribe.patients.HomeActivity;
import com.psychscribe.prelogin.model.SignInModel;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.DialogUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 28/7/16.
 */
public class ForgetPassDialogFragment extends DialogFragment {

    DialogForgetpassBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_forgetpass, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDialog();

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidate()){
                    attemptForgetPassWs();
                }
            }
        });
    }

    private void attemptForgetPassWs() {
        DialogUtils.showProgressDialog(getContext());
        ResponseInterface userDataRequest = APIRequest.provideInterface();
        HashMap<String, String> data = new HashMap<>();
        data.put(Constants.Email, binding.edtEmail.getText().toString());
        final Call<SignInModel> getUserCall = userDataRequest.forgetPass(data);

        getUserCall.enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call,
                                   Response<SignInModel> response) {
                DialogUtils.stopProgressDialog();
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()){
                        DialogUtils.showAlertDialog(new BaseActivity.OnDialogClick() {
                            @Override
                            public void onPositiveBtnClick() {
                                dismiss();
                            }

                            @Override
                            public void onNegativeBtnClick() {
                            }
                        }, getString(R.string.success), response.body().getMessage(), false, getContext());
                    }else{
                        DialogUtils.showAlertDialog(getContext(), getString(R.string.alert), getString(R.string.email_not_found));
                    }
                }
            }
            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                DialogUtils.stopProgressDialog();
            }
        });

    }

    private boolean isValidate() {
        if(!AppUtils.isOnline(getContext())) {
            DialogUtils.showAlertDialog(getContext(), getString(R.string.alert), getString(R.string.error_internet_connect));
            return false;
        } else if (TextUtils.isEmpty(binding.edtEmail.getText().toString().trim())) {
            DialogUtils.showAlertDialog(getContext(), getString(R.string.alert), getString(R.string.error_enter_email));
            return false;
        } else if(!AppUtils.isValidEmail(binding.edtEmail.getText().toString())) {
            DialogUtils.showAlertDialog(getContext(), getString(R.string.alert), getString(R.string.error_valid_email));
            return false;
        }
        return true;
    }

    private void initDialog() {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.ThemeDialogFit;
        WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
        wmlp.gravity = Gravity.FILL_HORIZONTAL;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
