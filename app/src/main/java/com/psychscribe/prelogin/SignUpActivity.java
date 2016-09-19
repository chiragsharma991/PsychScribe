package com.psychscribe.prelogin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.google.gson.JsonObject;
import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivitySignUpBinding;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 20/7/16.
 */
public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarColor(ContextCompat.getColor(this, R.color.colorAppAccent));
        mIsFromPreLogin = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        setupExlpodeWindowAnimations(Gravity.RIGHT);
    }

    protected void setupExlpodeWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    public void onClickSignIn(View view){
        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().getBoolean(Constants.ExtraFromHelp)){
                moveActivity(new Intent(this, SignInActivity.class), this);
                finish();
            }
        }else
            onBackPressed();
    }

    public void onClickResister(View view){
       if(isValidate()){
            checkEmailExist(binding.edtEmail.getText().toString());
        }
    }

    private boolean isValidate() {
        if(!isOnline(this)) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_internet_connect));
            return false;
        }else if (TextUtils.isEmpty(binding.edtUser.getText().toString().trim())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_name));
            binding.edtUser.requestFocus();
            return false;
        }  else if (TextUtils.isEmpty(binding.edtEmail.getText().toString().trim())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_email));
            binding.edtEmail.requestFocus();
            return false;
        } else if(!AppUtils.isValidEmail(binding.edtEmail.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_valid_email));
            binding.edtEmail.requestFocus();
            return false;
        } else if(TextUtils.isEmpty(binding.edtPassword.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_pass));
            binding.edtPassword.requestFocus();
            return false;
        } else if (binding.edtPassword.getText().toString().length() < 6) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_minimum_pass));
            binding.edtPassword.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(binding.etConfirmPass.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_confirm_pass));
            binding.etConfirmPass.requestFocus();
            return false;
        } else if (binding.etConfirmPass.getText().toString().length() < 6) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_minimum_confirm_pass));
            binding.etConfirmPass.requestFocus();
            return false;
        }else if (!binding.etConfirmPass.getText().toString().equals(binding.edtPassword.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_pass_match));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void checkEmailExist(String email) {
        showProgressDialog();
        ResponseInterface userDataRequest = APIRequest.provideInterface();
        HashMap<String, String> data = new HashMap<>();
        data.put(Constants.Email, email);
        Call<JsonObject> getUserCall = userDataRequest.
                checkEmailExist(data);

        getUserCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call,
                                   Response<JsonObject> response) {
                stopProgressDialog();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if(jsonObject.getBoolean("success")){
                            Intent intent = new Intent(SignUpActivity.this, PlanActivity.class);
                            intent.putExtra(Constants.ExtraName, binding.edtUser.getText().toString());
                            intent.putExtra(Constants.ExtraEmail, binding.edtEmail.getText().toString());
                            intent.putExtra(Constants.ExtraPassword, binding.edtPassword.getText().toString());
                            moveActivity(intent, SignUpActivity.this);
                        }else{
                            showAlertDialog(getString(R.string.alert), getString(R.string.email_enter_is_available));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showErrorLog("Error "+t.getMessage());
                stopProgressDialog();
            }
        });
    }
}
