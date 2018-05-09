package br.com.hitg.androidnotificationsamples;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NotificationsChooser extends AppCompatActivity {

    private static final String CHANNEL_1_ID = "CHANNEL_1";
    private static final String CHANNEL_2_ID = "CHANNEL_2";


    /*
    * Notification Helper Instance
    */
    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_chooser);
        notificationHelper = new NotificationHelper(this);
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            CharSequence name = CHANNEL_1_ID;
            //String description = getString(R.string.channel_description);
            String description = CHANNEL_1_ID;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onSimpleNotificationBtnClick(View view) {
        String title = "Simple Notification";
        String body = "This is a simple Notification.";

        NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(title, body);
        setDefaultPendingIntentOnNotification(nb);
        notificationHelper.notify(1, nb.build());
    }

    public void onExpandableNotificationText(View view) {
        String title = "Expandable Notification";
        String body = "This is a expandable Notification (TEXT)";
        String bigBody = "This is a expandable Notification. Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Cras sit amet pulvinar nulla, nec auctor est. Nulla" +
                " hendrerit imperdiet leo id tempus. Duis sit amet nisi nec risus tempor porta. " +
                "Pellentesque est nisi, ullamcorper ac iaculis vel, vestibulum eget neque. Duis " +
                "eleifend mauris et rutrum sollicitudin. Nam malesuada, enim a malesuada " +
                "accumsan, magna felis interdum enim, sit amet semper magna metus eu tellus. In " +
                "ante elit, eleifend ac sagittis quis, elementum nec mauris. Interdum et " +
                "malesuada fames ac ante ipsum primis in faucibus. Sed et odio pharetra, pretium " +
                "orci vel, ultrices mauris. Curabitur in commodo tellus. Quisque et risus dui. " +
                "Suspendisse ac odio ac nibh laoreet tincidunt nec vitae ex. Mauris in tellus et " +
                "tellus ullamcorper tincidunt quis et felis. Donec et ornare purus.";

        NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(title, body);
        setDefaultPendingIntentOnNotification(nb);
        Notification notification = new NotificationCompat.BigTextStyle(nb)
                .bigText(bigBody).build();
        notificationHelper.notify(2, notification);
    }

    private int notificationActionsQtd = 1;

    public void onSimpleNotificationWithAction(View view) {
        String title = "Notification With Action";
        String body = "This notification has some actions";

        NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(title, body);
        setDefaultPendingIntentOnNotification(nb);
        PendingIntent pendingIntent = getDefaultPendingIntent();

        if (notificationActionsQtd > 4) {
            notificationActionsQtd = 1;
        }

        switch (notificationActionsQtd) {
            case 1:
                nb.addAction(R.drawable.ic_sentiment_satisfied_black_24dp, "HAPPY", pendingIntent);
                break;
            case 2:
                nb.addAction(R.drawable.ic_sentiment_satisfied_black_24dp, "HAPPY", pendingIntent);
                nb.addAction(R.drawable.ic_sentiment_neutral_black_24dp, "BORED", pendingIntent);
                break;
            case 3:
                nb.addAction(R.drawable.ic_sentiment_satisfied_black_24dp, "HAPPY", pendingIntent);
                nb.addAction(R.drawable.ic_sentiment_neutral_black_24dp, "BORED", pendingIntent);
                nb.addAction(R.drawable.ic_sentiment_dissatisfied_black_24dp, "SAD", pendingIntent);
                break;
            case 4:
                nb.addAction(R.drawable.ic_sentiment_satisfied_black_24dp, "AAAAA BBBBB CCCCC " +
                        "DDDDD", pendingIntent);
                nb.addAction(R.drawable.ic_sentiment_neutral_black_24dp, "EEEEE FFFFF GGGGG " +
                        "HHHHH", pendingIntent);
                nb.addAction(R.drawable.ic_sentiment_dissatisfied_black_24dp, "IIIII JJJJJ KKKKK " +
                        "LLLLL", pendingIntent);
                break;
        }

        notificationHelper.notify(notificationActionsQtd * 100, nb.build());
        notificationActionsQtd++;
    }

    // Key for the string that's delivered in the action's intent.
    private static final String KEY_TEXT_REPLY = "key_text_reply";

    public void onNotificationWithReplyText(View view) {
        String title = "Notification With Reply";
        String body = "Reply to me =)";

        NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(title, body);
        setDefaultPendingIntentOnNotification(nb);


        String replyLabel = "REPLY";//getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        PendingIntent pendingIntent = getDefaultPendingIntent();
//        PendingIntent replyPendingIntent =
//                PendingIntent.getBroadcast(getApplicationContext(),
//                        conversation.getConversationId(),
//                        getMessageReplyIntent(conversation.getConversationId()),
//                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote input.
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_warning_black_24dp,
                        replyLabel, pendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        nb.addAction(action);

        notificationHelper.notify(3, nb.build());
    }

    public void onNotificationWithProgressBar(View view) {
        String title = "Progress Bar Notification";
        String body = "Downloading something (Or not)";

        final NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(title,
                body);


        // Issue the initial notification with zero progress
        final int PROGRESS_MAX = 100;
        nb.setProgress(PROGRESS_MAX, 0, false);
        nb.addAction(R.drawable.ic_sentiment_satisfied_black_24dp, "CANCEL",
                getDefaultPendingIntent());
        nb.setPriority(NotificationCompat.PRIORITY_LOW);
        notificationHelper.notify(10000, nb.build());

        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                for (int i = 0; i <= 100; i++) {
                    try {
                        this.publishProgress(i);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return 0;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                nb.setContentText("Download = " + values[0]);
                nb.setProgress(PROGRESS_MAX, values[0], false);
                notificationHelper.notify(10000, nb.build());
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                // Muitas atualizações seguidas, algumas são perdidas.
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nb.setContentText("Download complete");
                nb.setProgress(PROGRESS_MAX, 0, false);
                notificationHelper.notify(10000, nb.build());
            }
        }.execute(1, 1, 1);
    }


    private int imageToShow = 1;

    public void onExpandableNotificationImage(View view) {

        String title = "Expandable Notification Image";
        String body = "Image";

        Bitmap myBitmap;

        if (imageToShow == 1 || imageToShow > 2) {
            imageToShow = 1;
            myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable
                    .notification_800x400);
        } else {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable
                    .notification_720x720);
        }
        imageToShow++;


        NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(title, body);
        setDefaultPendingIntentOnNotification(nb);
        nb.setLargeIcon(myBitmap);
        Notification notification = new NotificationCompat.BigPictureStyle(nb)
                .bigPicture(myBitmap)
                .bigLargeIcon(null)
                .build();

        notificationHelper.notify(400 * imageToShow, notification);

    }

    public void onInboxNotification(View view) {
    }

    public void onMediaNotification(View view) {
    }

    public void onNotificationGroup(View view) {
        String msgText = "Jeally Bean Notification example!! "
                + "where you will see three different kind of notification. "
                + "you can even put the very long string here.";

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_1_ID);
        builder.setContentTitle("Big text Notification")
                .setContentText("Big text Notification")
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        Notification notification = new NotificationCompat.BigTextStyle(builder)
                .bigText(msgText).build();

        notificationManager.notify(1, notification);
        notificationManager.notify(2, notification);
        notificationManager.notify(3, notification);
        notificationManager.notify(4, notification);
        notificationManager.notify(5, notification);
    }

    public void onBadge(View view) {
    }

    public void onCustomNotification(View view) {

    }


    private void setDefaultPendingIntentOnNotification(NotificationCompat.Builder nb) {
        PendingIntent pendingIntent = getDefaultPendingIntent();
        nb.setContentIntent(pendingIntent);
    }

    private PendingIntent getDefaultPendingIntent() {
        Intent intent = new Intent(this, NotificationsChooser.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return pendingIntent;
    }
}
