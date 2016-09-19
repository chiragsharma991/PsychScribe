package com.psychscribe.prelogin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityPasswordResetBinding;
import com.psychscribe.prelogin.fragment.ResetCompleteFragment;
import com.psychscribe.prelogin.model.SignInModel;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordResetActivity extends BaseActivity {

    ActivityPasswordResetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarColor(ContextCompat.getColor(this, R.color.colorAppAccent));
        mIsFromPreLogin = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_reset);
        if(SharedPreferenceUtil.getBoolean(Constants.KEY_IS_LOGIN, false))
            finish();
    }

    public void onResetClick(View view){
        if(getIntent().getData() != null){
            if(isValidate()){
                attemptResetPass(getToken());
            }
        }else
            onBackPressed();
    }

    @Override
    public void onBackPressed() {
        onBackPressHandle();
    }

    public void onBackPressHandle() {
        FragmentManager manager = getSupportFragmentManager();
        int backStackEntryCount = manager.getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            moveToLogin();
        } else {
            super.onBackPressed();
        }
    }

    public void moveToLogin() {
        Intent intent = new Intent(PasswordResetActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        moveActivity(intent, PasswordResetActivity.this);
        finishWithHandler();
    }

    private String getToken() {
        Intent intent = getIntent();
        Uri data = intent.getData();
        showErrorLog(data.toString());
        // http://dev-imaginovation.net/rojas/resetlink/43afb51586ef90005f25d0fc282ef8808847e2603f0cf673592931e384f0fe7b
        String url = data.toString();
        return AppUtils.replaceAllSpecialCharacter(url.split("resetlink")[1]);
    }

    public void onCancelClick(View view){
       onBackPressed();
    }

    private boolean isValidate() {
        if(!isOnline(this)) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_internet_connect));
            return false;
        }  /*else if (TextUtils.isEmpty(binding.edtEmail.getText().toString().trim())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_email));
            binding.edtEmail.requestFocus();
            return false;
        } else if(!AppUtils.isValidEmail(binding.edtEmail.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_valid_email));
            binding.edtEmail.requestFocus();
            return false;
        } */else if(TextUtils.isEmpty(binding.edtPassword.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_pass));
            binding.edtPassword.requestFocus();
            return false;
        } else if (binding.edtPassword.getText().toString().length() < 6) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_minimum_pass));
            binding.edtPassword.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(binding.edtConfirmPassword.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_confirm_pass));
            binding.edtConfirmPassword.requestFocus();
            return false;
        } else if (binding.edtConfirmPassword.getText().toString().length() < 6) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_minimum_confirm_pass));
            binding.edtConfirmPassword.requestFocus();
            return false;
        }else if (!binding.edtConfirmPassword.getText().toString().equals(binding.edtPassword.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_pass_match));
            return false;
        }
        return true;
    }

    private void attemptResetPass(String token) {
        showProgressDialog();
        showErrorLog(""+token);
        ResponseInterface userDataRequest = APIRequest.provideInterface();
        HashMap<String, String> data = new HashMap<>();
        //data.put(Constants.Email, binding.edtEmail.getText().toString());
        data.put(Constants.Password, binding.edtPassword.getText().toString());
        data.put(Constants.PasswordConfirmation, binding.edtConfirmPassword.getText().toString());
        data.put(Constants.Token, token);
        Call<SignInModel> getUserCall = userDataRequest.
                resetPass(data);

        getUserCall.enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call,
                                   Response<SignInModel> response) {
                stopProgressDialog();
                    try {
                        if (response.isSuccessful()) {
                            if(response.body().getSuccess()){
                                addFragmentWithAnim(new ResetCompleteFragment(), "");
                            }else{
                                showAlertDialog(getString(R.string.alert), response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                showErrorLog("Error "+t.getMessage());
                stopProgressDialog();
            }
        });
    }
}
