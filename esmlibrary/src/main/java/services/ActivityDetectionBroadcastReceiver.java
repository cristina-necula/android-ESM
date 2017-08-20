package services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

import util.Constants;

/**
 * Created by Cristina on 8/20/2017.
 */

public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {

    public ArrayList<DetectedActivity> DetectedActivities;

    @Override
    public void onReceive(Context context, Intent intent) {
        DetectedActivities = intent.getParcelableArrayListExtra(Constants.ACTIVITY_EXTRA);

    }
}
