package services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

import util.Constants;

/**
 * Created by Cristina on 8/15/2017.
 */

public class DetectedActivitiesIntentService extends IntentService {

    protected static final String TAG = "DetectedActivitiesIntentService";

    public DetectedActivitiesIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onHandleIntent(Intent intent) {

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        localIntent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

}
