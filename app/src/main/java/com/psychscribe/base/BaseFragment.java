package com.psychscribe.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.psychscribe.R;

/**
 * Created by ubuntu on 20/7/16.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    private Dialog dialog;

    protected void statusBarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(color);
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

    protected void showProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog(getContext());
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

    protected void showToast(String msg){
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showSnackbar(View view, String msg){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    protected void showErrorLog(String error){
        Log.e(TAG, ""+error);
    }

    protected void showAlertDialog(final OnDialogClick onDialogClick){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("SCRUM");
        builder.setCancelable(false);
        builder.setMessage("In the SCRUM methodology a sprint is the basic unit of development. Each sprint is preceded by a planning meeting, where the tasks for the sprint are identified and an estimated commitment for the sprint goal is made, and followed by a review or retrospective meeting where the progress is reviewed and lessons for the next sprint are identified. During each sprint, the team creates finished portions of a product.....");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogClick.onPositiveBtnClick();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogClick.onNegativeBtnClick();
            }
        });
        builder.show();
    }

    protected void showAlertDialog(String title, String msg){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
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
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "").commit();*/
    }

    protected void replaceFragmentWithAnim(Fragment fragment, String backStack){
        /*getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out).
                replace(R.id.container, fragment, "").commit();*/
    }

    protected void addFragmentWithAnim(Fragment fragment, String backStack){
        getActivity().getSupportFragmentManager().beginTransaction().
            //setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out).
                add(R.id.container, fragment, "").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(backStack).commit();
    }

    protected void addFragment(Fragment fragment, String backStack){
        /*getSupportFragmentManager().beginTransaction().
                add(R.id.container, fragment, "").addToBackStack(backStack).commit();*/
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



}
