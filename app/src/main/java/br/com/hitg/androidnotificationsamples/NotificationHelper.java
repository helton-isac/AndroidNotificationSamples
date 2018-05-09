package br.com.hitg.androidnotificationsamples;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    private NotificationManager manager;

    public static final String CHANNEL_DEFAULT_ID = "CHANNEL_DEFAULT";
    public static final String CHANNEL_SECONDARY_ID = "CHANNEL_SECONDARY";


    public NotificationHelper(Context base) {
        super(base);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel chan1 = null;
            chan1 = new NotificationChannel(CHANNEL_DEFAULT_ID,
                    getString(R.string.notification_channel_default_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setDescription(getString(R.string.notification_channel_default_desc));
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(chan1);

            NotificationChannel chan2 = new NotificationChannel(CHANNEL_SECONDARY_ID,
                    getString(R.string.notification_channel_secondary_name), NotificationManager
                    .IMPORTANCE_HIGH);
            chan1.setDescription(getString(R.string.notification_channel_secondary_desc));
            chan2.setLightColor(Color.BLUE);
            chan2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan2);
        }
    }

    /**
     * Get the notification manager.
     * <p>
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }


    /**
     * Get a Default notification
     * <p>
     * Provide the builder rather than the notification it's self as useful for making notification
     * changes.
     *
     * @param title the title of the notification
     * @param body  the body text for the notification
     * @return the builder as it keeps a reference to the notification (since API 24)
     */
    public NotificationCompat.Builder getDefaultNotification(String title, String body) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_DEFAULT_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setAutoCancel(true);
    }

    /**
     * Send a notification.
     *
     * @param id           The ID of the notification
     * @param notification The notification object
     */
    public void notify(int id, Notification notification) {
        getManager().notify(id, notification);
    }

}
