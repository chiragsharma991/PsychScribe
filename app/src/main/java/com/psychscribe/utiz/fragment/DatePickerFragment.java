package com.psychscribe.utiz.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener listener;
    boolean mBirthDayCal;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        DatePickerDialog datePickerDialog =  new DatePickerDialog(getActivity(), listener, year, month, day);
        if(mBirthDayCal)
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        return datePickerDialog;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener, boolean isBirthDayCal){
        this.listener = listener;
        mBirthDayCal = isBirthDayCal;
    }
}