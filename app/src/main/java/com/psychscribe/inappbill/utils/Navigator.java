package com.psychscribe.inappbill.utils;

import android.app.Activity;
import android.content.Intent;

import com.psychscribe.inappbill.ui.PurchasePassportActivity;


/**
 * CommonAsync wrapper around the Android Intent mechanism
 * 
 * @author Blundell
 * 
 */
public class Navigator {

    public static final int REQUEST_PASSPORT_PURCHASE = 2012;

    private final Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }



    public void toPurchasePassportActivityForResult() {
        Intent intent = new Intent(activity, PurchasePassportActivity.class);
        activity.startActivityForResult(intent, REQUEST_PASSPORT_PURCHASE);
    }

}
