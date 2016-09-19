package com.psychscribe.base;

import android.app.Application;
import android.content.Context;

import com.psychscribe.rest.OkClientFactory;
import com.psychscribe.sqlite.DatabaseHelper;
import com.psychscribe.sqlite.DatabaseManager;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.font.FontCache;

import okhttp3.OkHttpClient;

/**
 * Created by ubuntu on 20/7/16.
 */
public class PsychScribeApp extends Application {

    private static Context context;
    private static PsychScribeApp mInstance = null;
    private static OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        context = getApplicationContext();

        SharedPreferenceUtil.init(this);

        /**
         * Database create
         */
        DatabaseManager.initializeInstance(new DatabaseHelper(this));

        mOkHttpClient = OkClientFactory.provideOkHttpClient(this);
        /*
        *  Add Font using binding
        *
         */
        FontCache.getInstance().addFont("bold","Raleway_Bold.ttf");
        FontCache.getInstance().addFont("regular","Raleway_Regular.ttf");
        FontCache.getInstance().addFont("medium","Raleway_Medium.ttf");


    }

    public static Context getAppContext() {
        return context;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static PsychScribeApp getInstance() {
        return mInstance;
    }
}
