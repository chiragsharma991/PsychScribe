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
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.psychscribe.R;
import com.psychscribe.inappbill.base.BlundellActivity;
import com.psychscribe.inappbill.util.IabHelper;
import com.psychscribe.inappbill.utils.Navigator;
import com.psychscribe.prelogin.SignInActivity;
import com.psychscribe.receiver.AppLogoutAlarmReceiver;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.customvViews.CircularJumpProgress;

import java.util.Calendar;

/**
 * Created by ubuntu on 20/7/16.
 */
public abstract class BaseActivity extends BlundellActivity {

    private static final String TAG = "BaseActivity";
    protected int ANIM_TIME = 300;
    protected boolean mIsFromPreLogin = false;  // For check logout
    private Dialog dialog;
    protected int FINISH_TIME = 400;

    protected boolean checkPermission(String strPermission, Context context){
        int result = ContextCompat.checkSelfPermission(context, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    protected void finishWithHandler(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, FINISH_TIME);
    }

    protected void statusBarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
        }
    }


    protected void closeKeyBoard(Activity context) {
        View view =  context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * onUserInteraction()  and initAppLogoutAlarm() for using
     *                             app in Ideal state and logout
     *
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        initAppLogoutAlarm();
    }

    protected void initAppLogoutAlarm() {
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

    protected void setupExlpodeWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode fade = new Explode();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    protected void setupSlideWindowAnimations(int startGravity, int endGravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(startGravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(slide);

            slide = new Slide(endGravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
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
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
            } else {
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mIsFromPreLogin)
            checkAppLogout();
    }

    private void checkAppLogout() {
        if(!SharedPreferenceUtil.getBoolean(Constants.KEY_IS_LOGIN, false)) {
            Intent intent =new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            moveActivity(intent, BaseActivity.this);
            finish();
        }
    }

    protected void showAlertDialog(final OnDialogClick onDialogClick, String title, String msg, boolean isNegative){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(isNegative ? "Yes" : "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogClick.onPositiveBtnClick();
            }
        });
        if(isNegative)
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
        /*getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out).
                replace(R.id.container, fragment, "").commit();*/
    }

    protected void addFragmentWithFadeAnim(Fragment fragment, String backStack){
       /* getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out).
                add(R.id.container, fragment, "").addToBackStack(backStack).commit();*/
    }

    protected void addFragmentWithSlideAnim(Fragment fragment, String backStack){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.slide_out_right).
                add(R.id.container, fragment, "").addToBackStack(backStack).commit();
    }

    protected void addFragment(Fragment fragment, String backStack){
        /*getSupportFragmentManager().beginTransaction().
                add(R.id.container, fragment, "").addToBackStack(backStack).commit();*/
    }

    protected void addFragmentWithAnim(Fragment fragment, String backStack){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, "").addToBackStack(backStack).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
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

    public void onPurchaseItemClick() {
        navigate().toPurchasePassportActivityForResult();
    }
}
