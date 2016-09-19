package com.psychscribe.prelogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.patients.HomeActivity;
import com.psychscribe.services.GetCommonDataService;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.Constants;

/**
 * Created by ubuntu on 20/7/16.
 */
public class SplashActivity extends BaseActivity{

    private static final int SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppLogoutAlarm();
        startCommonService();
        mIsFromPreLogin = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPreferenceUtil.getBoolean(Constants.KEY_IS_LOGIN, false)){
                    moveActivity(new Intent(SplashActivity.this, HomeActivity.class), SplashActivity.this);
                }else {
                    if(SharedPreferenceUtil.getBoolean(Constants.KEY_IS_HELP_SCREEN_DONE, false))
                        moveActivity(new Intent(SplashActivity.this, SignInActivity.class), SplashActivity.this);
                    else
                        moveActivity(new Intent(SplashActivity.this, HelpActivity.class), SplashActivity.this);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 400);

            }
        },SPLASH_TIMEOUT);
    }

    void startCommonService() {
        Intent intent = new Intent(this, GetCommonDataService.class);
        startService(intent);
    }

}
