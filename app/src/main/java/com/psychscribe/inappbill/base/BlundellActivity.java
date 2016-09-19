package com.psychscribe.inappbill.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.psychscribe.inappbill.utils.Navigator;
import com.psychscribe.inappbill.utils.Toaster;


/**
 * Here we keep common functionality that will be used across multiple activities
 * making our life easier
 * 
 * @author Blundell
 * 
 */
public abstract class BlundellActivity extends AppCompatActivity {

    private Navigator navigator;
    private Toaster toaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = new Navigator(this);
        toaster = new Toaster(this);
    }

    protected Navigator navigate() {
        return navigator;
    }

    protected void popBurntToast(String msg) {
        toaster.popBurntToast(msg);
    }

    protected void popToast(String msg) {
        toaster.popToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigator = null;
        toaster = null;
    }
}
