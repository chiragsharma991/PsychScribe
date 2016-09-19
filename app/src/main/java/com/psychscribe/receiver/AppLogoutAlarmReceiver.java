/**
 * Created by Darshan Shah on 10/18/2015 5:27 PM.
 */

package com.psychscribe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.Constants;

/**
 * Created by Darshan Shah on 10/18/2015 5:27 PM.
 */
public class AppLogoutAlarmReceiver extends BroadcastReceiver
{
    //private static final int NotificationId = 101;
    //private static final String Summary_Text = "Have you tried workout?";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(SharedPreferenceUtil.getBoolean(Constants.KEY_IS_LOGIN, false)) {
            SharedPreferenceUtil.putValue(Constants.KEY_IS_LOGIN, false);
            SharedPreferenceUtil.save();
        }
    }

    /*public void generateNotification(Context context) {
        Bitmap icon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        Intent resultIntent = new Intent(context, HomeActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0, resultIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(Summary_Text)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(icon1)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND).build();
        mNotificationManager.notify(NotificationId, notification);

    }

    public int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.ic_launcher : R.drawable.ic_launcher;
    }*/
}
