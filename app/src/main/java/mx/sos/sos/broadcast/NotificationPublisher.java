package mx.sos.sos.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class NotificationPublisher extends BroadcastReceiver{

    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = ( NotificationManager )
                context.getSystemService( Context.NOTIFICATION_SERVICE );

        Notification notification = intent.getParcelableExtra( NOTIFICATION );
        int id = intent.getIntExtra( NOTIFICATION_ID, 0 );
        notificationManager.notify( id, notification );
    }
}
