package com.esm.android.androidesm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.reflect.Array;

import models.firebase.Survey;
import tracker.EsmTracker;

/**
 * Created by Cristina on 8/27/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.d("time", System.currentTimeMillis() + "");
            Object[] values = remoteMessage.getData().values().toArray();
            String surveyKey = (String)values[0];
            String workflowKey = (String)values[1];
            EsmTracker.getInstance().showSurvey(surveyKey, workflowKey);
        }

    }

}
