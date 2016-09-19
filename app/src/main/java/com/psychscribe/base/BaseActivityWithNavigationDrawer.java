package com.psychscribe.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psychscribe.R;
import com.psychscribe.base.adapter.NavigationMenuAdapter;
import com.psychscribe.databinding.ActivityBaseNavigationDrawerBinding;
import com.psychscribe.inappbill.base.BlundellActivity;
import com.psychscribe.patients.HomeActivity;
import com.psychscribe.prelogin.SignInActivity;
import com.psychscribe.profile.ProfileActivity;
import com.psychscribe.receiver.AppLogoutAlarmReceiver;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.upgradePlan.UpgradePlanActivity;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.RecyclerTouchListener;
import com.psychscribe.utiz.customvViews.CircularJumpProgress;

import java.util.Calendar;

/**
 * Created by ubuntu on 20/7/16.
 */
public abstract class BaseActivityWithNavigationDrawer extends BlundellActivity {

    private static final String TAG = "BaseActivity";
    protected Toolbar mToolbar;
    protected TextView txtToolbarTitle;
    protected ActionBarDrawerToggle mToggle;
    protected DrawerLayout mDrawer;
    protected FrameLayout mActivityContent;
    private boolean isDisableDrawer;
    protected int ANIM_TIME = 300;
    private RecyclerView mRecyclerViewMenu;
    private ActivityBaseNavigationDrawerBinding binding;
    private Dialog dialog;
    protected int FINISH_TIME = 400;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base_navigation_drawer);
        //setContentView(R.layout.activity_base_navigation_drawer);
        init();
        setRecyclerAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAppLogout();
    }

    private void checkAppLogout() {
        if(!SharedPreferenceUtil.getBoolean(Constants.KEY_IS_LOGIN, false)) {
            Intent intent =new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            moveActivity(intent, BaseActivityWithNavigationDrawer.this);
            finish();
        }
    }

    private void setRecyclerAdapter() {
        mRecyclerViewMenu.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        mRecyclerViewMenu.setLayoutManager(linearLayoutManager);
        NavigationMenuAdapter mAdapter = new NavigationMenuAdapter(this);
        mRecyclerViewMenu.setAdapter(mAdapter);
        mRecyclerViewMenu.addOnItemTouchListener(new RecyclerTouchListener(mRecyclerViewMenu, new RecyclerTouchListener.OnRecyclerClickListener() {
            @Override
            public void onClick(View v, int position) {
                switch (position){
                    case 0:
                        moveActivity(new Intent(BaseActivityWithNavigationDrawer.this, HomeActivity.class), BaseActivityWithNavigationDrawer.this);
                        finishWithHandler();
                        break;
                    case 1:
                        moveActivity(new Intent(BaseActivityWithNavigationDrawer.this, ProfileActivity.class), BaseActivityWithNavigationDrawer.this);
                        finishWithHandler();
                        break;
                    case 2:
                        moveActivity(new Intent(BaseActivityWithNavigationDrawer.this, UpgradePlanActivity.class), BaseActivityWithNavigationDrawer.this);
                        finishWithHandler();
                        break;
                    case 3:

                        break;
                    case 4:
                        logout();
                        break;
                }
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    protected void finishWithHandler(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, FINISH_TIME);
    }

    private void logout() {
        showAlertDialog(new OnDialogClick() {
            @Override
            public void onPositiveBtnClick() {
                SharedPreferenceUtil.putValue(Constants.KEY_IS_LOGIN, false);
                SharedPreferenceUtil.save();
                moveActivity(new Intent(BaseActivityWithNavigationDrawer.this, SignInActivity.class), BaseActivityWithNavigationDrawer.this);
                finishWithHandler();
            }

            @Override
            public void onNegativeBtnClick() {

            }
        }, getString(R.string.logout), getString(R.string.sure_logout), true);
    }

    /**
     * onUserInteraction()  and initAppLogoutAlarm() for using
     *                             app in Ideal state and logout
     *
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        initAppOpenAlarm();
    }

    protected void initAppOpenAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AppLogoutAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
        alarmMgr.set(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), alarmIntent);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        txtToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        setTitle("");
        mActivityContent = (FrameLayout)findViewById(R.id.activityContent);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRecyclerViewMenu = (RecyclerView) findViewById(R.id.recyclerMenu);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    protected void syncToolbarState(){
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    protected FrameLayout getMiddleContent() {
        return mActivityContent;
    }

    protected void disableMenuAndHideToolbar(){
        isDisableDrawer = true;
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().hide();
    }

    protected void statusBarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onBackPressed() {
        if(isDisableDrawer){
            super.onBackPressed();
        }else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    protected void openCloseDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    protected boolean checkPermission(String strPermission, Context context){
        int result = ContextCompat.checkSelfPermission(context, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    protected void closeKeyBoard(Activity context) {
        View view =  context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected void showToast(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showSnackbar(View view, String msg){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    protected void showErrorLog(String error){
        Log.e(TAG, ""+error);
    }

    protected void moveActivity(Intent intent, Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
        } else {
            startActivity(intent);
        }
    }

    protected void setupWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode fade = new Explode();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    protected void setupSlideWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    protected void showAlertDialog(final OnDialogClick onDialogClick, String title, String msg, boolean isNagative){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogClick.onPositiveBtnClick();
            }
        });
        if(isNagative)
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onDialogClick.onNegativeBtnClick();
                }
            });
        builder.show();
    }

    protected void showAlertDialog(String title, String msg){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    public interface OnDialogClick {
        void onPositiveBtnClick();
        void onNegativeBtnClick();
    }

    protected void replaceFragment(Fragment fragment, String backStack){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "").commit();
    }

    protected void replaceFragmentWithAnim(Fragment fragment, String backStack){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out).
                replace(R.id.container, fragment, "").commit();
    }

    protected void addFragmentWithAnim(Fragment fragment, String backStack){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out).
                add(R.id.container, fragment, "").addToBackStack(backStack).commit();
    }

    protected void addFragmentWithSlideAnim(Fragment fragment, String backStack){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, "").addToBackStack(backStack).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    protected void addFragment(Fragment fragment, String backStack){
        /*getSupportFragmentManager().beginTransaction().
                add(R.id.container, fragment, "").addToBackStack(backStack).commit();*/
    }

    protected void showProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_loader);
        dialog.show();
    }

    protected void stopProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
    }

    protected void transitionToActivity(Intent intent, Activity context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(context, view, "profile");
            startActivity(intent, options.toBundle());
        }else {
            startActivity(intent);
        }
    }
}
