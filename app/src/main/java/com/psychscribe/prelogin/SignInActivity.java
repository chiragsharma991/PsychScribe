package com.psychscribe.prelogin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivitySignInBinding;
import com.psychscribe.patients.HomeActivity;
import com.psychscribe.prelogin.fragment.ForgetPassDialogFragment;
import com.psychscribe.prelogin.model.SignInModel;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.fragment.StateDialogFragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 20/7/16.
 */
public class SignInActivity extends BaseActivity{

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarColor(ContextCompat.getColor(this, R.color.colorAppAccent));
        mIsFromPreLogin = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        setupExlpodeWindowAnimations(Gravity.BOTTOM);
    }

    public void onLoginClick(View view){
        if(isValidate()){
            attempLogin();
        }
    }

    public void onForgetPassClick(View view){
            openForgetPassDialog();
    }

    private void openForgetPassDialog() {
        ForgetPassDialogFragment dialogFragment = new ForgetPassDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    public void onSignUpClick(View view){
        moveActivity(new Intent(this, SignUpActivity.class), this);
    }

    private boolean isValidate() {
        if(!isOnline(this)) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_internet_connect));
            return false;
        } else if (TextUtils.isEmpty(binding.etLoginEmail.getText().toString().trim())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_email));
            return false;
        } else if(!AppUtils.isValidEmail(binding.etLoginEmail.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_valid_email));
            return false;
        } else if(TextUtils.isEmpty(binding.edtPassword.getText().toString())) {
            showAlertDialog(getString(R.string.alert), getString(R.string.error_enter_pass));
            return false;
        }
        return true;
    }

    private void attempLogin() {
        showProgressDialog();
        ResponseInterface userDataRequest = APIRequest.provideInterface();
        HashMap<String, String> data = new HashMap<>();
        data.put(Constants.Email, binding.etLoginEmail.getText().toString());
        data.put(Constants.Password, binding.edtPassword.getText().toString());
        data.put(Constants.DeviceToken, "619");
        data.put(Constants.DeviceType, "android");
        Call<SignInModel> getUserCall = userDataRequest.getLoginResponse(data);

        getUserCall.enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call,
                                   Response<SignInModel> response) {
                stopProgressDialog();
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()){
                        SharedPreferenceUtil.putValue(Constants.KEY_IS_LOGIN, true);
                        SharedPreferenceUtil.putValue(Constants.KEY_EMAIL, response.body().getData().get(0).getEmail());
                        SharedPreferenceUtil.putValue(Constants.KEY_IS_PAID, response.body().getData().get(0).getIsPaid());
                        SharedPreferenceUtil.putValue(Constants.KEY_PASSWORD, response.body().getData().get(0).getPasscode());
                        SharedPreferenceUtil.putValue(Constants.KEY_USER_ID, response.body().getData().get(0).getId());
                        SharedPreferenceUtil.putValue(Constants.KEY_USER_NAME, response.body().getData().get(0).getName());
                        SharedPreferenceUtil.putValue(Constants.KEY_TOKEN, response.body().getData().get(0).getToken());
                        SharedPreferenceUtil.save();
                        moveActivity(new Intent(SignInActivity.this, HomeActivity.class), SignInActivity.this);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 400);
                    }else{
                        showAlertDialog(getString(R.string.alert), getString(R.string.email_pass_wrong));
                    }
                }
            }
            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                showErrorLog("Error "+t.getMessage());
                stopProgressDialog();
            }
        });
    }

    protected void setupExlpodeWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           /* Fade fade = new Fade();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);*/

            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }



}
