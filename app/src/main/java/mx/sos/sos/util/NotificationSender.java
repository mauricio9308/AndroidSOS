package mx.sos.sos.util;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import mx.sos.sos.R;
import mx.sos.sos.broadcast.NotificationPublisher;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class NotificationSender {

    // TODO add support for ICS
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static Notification buildNotification( Context context, String title, String content ){
        Notification.Builder builder = new Notification.Builder( context );
        builder.setContentTitle( title );
        builder.setContentText( content );
        builder.setSmallIcon( R.mipmap.ic_launcher );
        return builder.build();
    }

    public static void scheduleNotification(Context context, Notification notification, int delay) {

        Intent notificationIntent = new Intent( context , NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context , 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
