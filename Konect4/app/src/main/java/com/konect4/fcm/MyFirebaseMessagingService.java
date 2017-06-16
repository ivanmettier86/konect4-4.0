package com.konect4.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.konect4.R;
import com.konect4.activities.SuggestGroup;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;

/**
 * Created by Muhammad Zeeshan on 6/7/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String id, type, message, title, notification_img, click_action;
    public static int notification_id=0;

    //   onmessagereceived firebase not called when application not running

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + "MZ is testing here");   //remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            id=remoteMessage.getData().get("id");
            type=remoteMessage.getData().get("type");
            title=remoteMessage.getData().get("title");
            message=remoteMessage.getData().get("body");
            notification_img=remoteMessage.getData().get("icon");
            click_action=remoteMessage.getData().get("click_action");
        }

        Log.d(TAG, "Message data Notification Data: " + remoteMessage.toString());


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getIcon());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String id, String type, String messageBody, String title) {

//        http://stackoverflow.com/questions/37407366/firebase-fcm-notifications-click-action-payload

        Intent intent;
        intent = new Intent(this, SuggestGroup.class);

        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("is_notification", "yes");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notification_id++;
        notificationManager.notify(requestID + 100 /* ID of notification */, notificationBuilder.build());   //  Integer.parseInt(id)
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}