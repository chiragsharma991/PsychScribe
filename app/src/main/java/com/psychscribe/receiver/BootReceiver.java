package com.psychscribe.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.Constants;

import java.util.Calendar;

/**
 * Created by ubuntu on 6/7/16.
 */
public class BootReceiver extends BroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(SharedPreferenceUtil.getBoolean(Constants.KEY_IS_LOGIN, false))
            initAppOpenAlarm(context);
    }

    private void initAppOpenAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AppLogoutAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
        alarmMgr.set(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), alarmIntent);
    }
}
