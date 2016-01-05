package com.dd.realmbrowser;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by pengfeixie on 16/1/5.
 */
public final class FuckBrowser {

    public static final int NOTIFICATION_ID = 1000;

    private static final FuckBrowser sInstance = new FuckBrowser();
    private List<Class<? extends RealmObject>> mRealmModelList;

    private FuckBrowser() {
        mRealmModelList = new ArrayList<>();
    }

    public List<Class<? extends RealmObject>> getRealmModelList() {
        return mRealmModelList;
    }

    @SafeVarargs
    public final void addRealmModel(Class<? extends RealmObject>... arr) {
        mRealmModelList.addAll(Arrays.asList(arr));
    }

    public static FuckBrowser getInstance() {
        return sInstance;
    }

    public static void startRealmFilesActivity(@NonNull Activity activity) {
        FuckActivity.start(activity);
    }

    public static void startRealmModelsActivity(@NonNull Activity activity, @NonNull String realmFileName) {
        FuckModelActivity.start(activity, realmFileName);
    }

    public static void showRealmFilesNotification(@NonNull Activity activity) {
        showRealmNotification(activity, FuckActivity.class);
    }

    private static void showRealmNotification(@NonNull Activity activity, @NonNull Class activityClass) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.drawable.ic_rb)
                .setContentTitle(activity.getString(R.string.rb_title))
                .setContentText(activity.getString(R.string.rb_click_to_launch))
                .setAutoCancel(false);
        Intent notifyIntent = new Intent(activity, activityClass);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(activity, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
