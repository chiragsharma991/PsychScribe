package com.psychscribe.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.psychscribe.model.StateModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StateHandler {

    private static final String TABLE_STATE = "StateInfo";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CNTRY_CODE = "country_code";
    private static final String KEY_STATE_ID = "state_id";

    public String CREATE_TABLE = " CREATE TABLE " + TABLE_STATE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_CNTRY_CODE + " TEXT NOT NULL, " + KEY_STATE_ID + " TEXT NOT NULL, " +
            KEY_NAME + " TEXT NOT NULL); ";

    private SQLiteDatabase mDb;

    public void createState(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public long addState(StateModel.StateData _stateModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, _stateModel.getName());
        initialValues.put(KEY_CNTRY_CODE, _stateModel.getCountryId());
        initialValues.put(KEY_STATE_ID, _stateModel.getId());
        mDb = DatabaseManager.getInstance().openDatabase();
        return mDb.insert(TABLE_STATE, null, initialValues);
    }


    public boolean deleteState(String _id) {
        Log.e("", "_id " + _id);
        mDb = DatabaseManager.getInstance().openDatabase();
        return mDb.delete(TABLE_STATE, "id=" + _id, null) > 0;
    }

    public boolean deleteState() {
        mDb = DatabaseManager.getInstance().openDatabase();
        return mDb.delete(TABLE_STATE, null, null) > 0;
    }

    public ArrayList<StateModel.StateData> getAllState() {

        ArrayList<StateModel.StateData> arrayList = new ArrayList<>();

        try {
            mDb = DatabaseManager.getInstance().openDatabase();
            Cursor mCursor = mDb.query(TABLE_STATE, null, null, null, null, null, KEY_ID + " DESC");

            mCursor.moveToNext();
            for (int i = 0; i < mCursor.getCount(); i++) {
                StateModel.StateData stateData = new StateModel().new StateData();
                //stateData.id = String.valueOf(mCursor.getInt(Integer.valueOf(mCursor.getColumnIndex(KEY_ID))));
                stateData.setName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                stateData.setCountryId(mCursor.getString(mCursor.getColumnIndex(KEY_CNTRY_CODE)));
                stateData.setId(Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(KEY_STATE_ID))));
                arrayList.add(stateData);
                mCursor.moveToNext();
            }
            sortAlphabeticAscendingList(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    private void sortAlphabeticAscendingList(List<StateModel.StateData> groupContactModelList) {
        Collections.sort(groupContactModelList, new Comparator<StateModel.StateData>() {
            @Override
            public int compare(StateModel.StateData s1, StateModel.StateData s2)
            {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
    }

    public ArrayList<StateModel> getStateFromID(String countryId) {
        Log.e("countryId", "" + countryId);
        ArrayList<StateModel> arrayList = new ArrayList<>();
        try {
            mDb = DatabaseManager.getInstance().openDatabase();
            Cursor mCursor = mDb.query(TABLE_STATE, null, KEY_CNTRY_CODE + " = ?", new String[]{countryId}, null, null, null);
            if (mCursor != null && mCursor.getCount() > 0) {
                mCursor.moveToNext();
                for (int i = 0; i < mCursor.getCount(); i++) {
                    StateModel unit_model = new StateModel();
                  //  unit_model.id = String.valueOf(mCursor.getInt(Integer.valueOf(mCursor.getColumnIndex(KEY_ID))));
                    String upperString = mCursor.getString(mCursor.getColumnIndex(KEY_NAME)).substring(0, 1).toUpperCase() + mCursor.getString(mCursor.getColumnIndex(KEY_NAME)).substring(1);
                 //   unit_model.name = upperString;
                //    unit_model.countryCode = mCursor.getString(mCursor.getColumnIndex(KEY_CNTRY_CODE));
                //    unit_model.stateid = mCursor.getString(mCursor.getColumnIndex(KEY_STATE_ID));
                    arrayList.add(unit_model);
                    mCursor.moveToNext();
                }
            } else {
                return arrayList;
            }
        } catch (Exception e) {
            return arrayList;
        }
        return arrayList;
    }

    public ArrayList<StateModel> getStateFromName(String stateName) {
        Log.e("stateName",""+stateName);
        Cursor cursor = null;
        ArrayList<StateModel> arrayList = new ArrayList<>();
        try {
            mDb = DatabaseManager.getInstance().openDatabase();
            String qry= "SELECT * FROM StateInfo where name = '"+stateName+"'";
            cursor = mDb.rawQuery(qry, null);
            while (cursor.moveToNext())
            {
                StateModel unit_model = new StateModel();
              //  unit_model.id = String.valueOf(cursor.getInt(Integer.valueOf(cursor.getColumnIndex(KEY_ID))));
                String upperString = cursor.getString(cursor.getColumnIndex(KEY_NAME)).substring(0, 1).toUpperCase() + cursor.getString(cursor.getColumnIndex(KEY_NAME)).substring(1);
               // unit_model.name = upperString;
              //  unit_model.countryCode = cursor.getString(cursor.getColumnIndex(KEY_CNTRY_CODE));
              //  unit_model.stateid = cursor.getString(cursor.getColumnIndex(KEY_STATE_ID));
                arrayList.add(unit_model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DatabaseUtil.closeResource(mDb, null, cursor);
        }
        return arrayList;
    }
}
