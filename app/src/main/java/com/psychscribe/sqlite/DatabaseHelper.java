package com.psychscribe.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "PsychScribe.db";

    private static String TAG = "TAG";

    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("Create table", "Create Table");
        try {
            StateHandler stateHandler = new StateHandler();
            stateHandler.createState(sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}