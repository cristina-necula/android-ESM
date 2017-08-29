package extensions;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.esm.android.esmlibrary.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import services.ActivityDetectionBroadcastReceiver;
import services.DetectedActivitiesIntentService;
import tracker.EsmTracker;
import util.Constants;


/**
 * Created by Cristina on 8/13/2017.
 */

public class EsmBaseActivity
        extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status> {

    //region Variables
    public FusedLocationProviderClient FusedLocationClient;
    public Location LastKnownLocation;
    public GoogleApiClient GoogleApiClient;
    public int MainActivityContainerId;
    protected static final String TAG = "BaseActivity";

    protected ActivityDetectionBroadcastReceiver mBroadcastReceiver;
    protected GoogleApiClient mGoogleApiClient;
    private ArrayList<DetectedActivity> mDetectedActivities;
    //endregion

    //region Base Activity Functions
    // call this in app activity to register for location updates
    public void requestUserLocation(){
        FusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    // call this in app activity to register for activity recognition
    public void requestRecognitionOfUserActivity(Bundle savedInstanceState){
        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
        if (savedInstanceState != null && savedInstanceState.containsKey(
                Constants.DETECTED_ACTIVITIES)) {
            mDetectedActivities = (ArrayList) savedInstanceState.getSerializable(
                    Constants.DETECTED_ACTIVITIES);
        } else {
            mDetectedActivities = new ArrayList<>();
            for (int i = 0; i < Constants.MONITORED_ACTIVITIES.length; i++) {
                mDetectedActivities.add(new DetectedActivity(Constants.MONITORED_ACTIVITIES[i], 0));
            }
        }
        buildGoogleApiClient();
        requestActivityUpdates();
    }

    // call this to stop receiving activity updates
    public void removeActivityUpdates() {
        if (!mGoogleApiClient.isConnected()) {
            return;
        }

        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                mGoogleApiClient,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
    }
    //endregion

    //region Activiy Recognition Functions

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void requestActivityUpdates() {
        if (!mGoogleApiClient.isConnected()) {
            return;
        }
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                mGoogleApiClient,
                Constants.DETECTION_INTERVAL_IN_MILLISECONDS,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
    }

    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            // Toggle the status of activity updates requested, and save in shared preferences.
            boolean requestingUpdates = !getUpdatesRequestedState();
            setUpdatesRequestedState(requestingUpdates);

            // Update the UI. Requesting activity updates enables the Remove Activity Updates
            // button, and removing activity updates enables the Add Activity Updates button.
            // TODO - do something

            Toast.makeText(
                    this,
                    getString(requestingUpdates ? R.string.activity_updates_added :
                            R.string.activity_updates_removed),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Log.e(TAG, "Error adding or removing activity detection: " + status.getStatusMessage());
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(Constants.DETECTED_ACTIVITIES, mDetectedActivities);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setUpdatesRequestedState(boolean requestingUpdates) {
        getSharedPreferencesInstance()
                .edit()
                .putBoolean(Constants.ACTIVITY_UPDATES_REQUESTED_KEY, requestingUpdates)
                .apply();
    }

    private boolean getUpdatesRequestedState() {
        return getSharedPreferencesInstance()
                .getBoolean(Constants.ACTIVITY_UPDATES_REQUESTED_KEY, false);
    }

    private SharedPreferences getSharedPreferencesInstance() {
        return getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //endregion

    //region Override methods
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(Constants.BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();
        EsmTracker.getInstance().addActivityStartedEvent(this);
    }

    //endregion

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        FusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            LastKnownLocation = task.getResult();
                        } else {
                            Log.w("LOCATION", "getLastLocation:exception", task.getException());
                        }
                    }
                });
    }

    public String getLastLocationName(){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        if(!geocoder.isPresent())
            return "";
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(
                    LastKnownLocation.getLatitude(),
                    LastKnownLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null & list.size() > 0) {
            Address address = list.get(0);
            return address.getLocality();
        }

        return null;
    }

    private void showSnackbar(final String text) {
        View container = findViewById(MainActivityContainerId);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                Constants.REQUEST_LOCATION_PERMISSIONS_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {
            Log.i("LOCATION", "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.location_permissions_relationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i("LOCATION", "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_LOCATION_PERMISSIONS_CODE) {
            if (grantResults.length <= 0) {
                Log.i("LOCATION", "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    public String getActivityName(){
        return this.getClass().getSimpleName();
    }

    @Override
    public void onBackPressed() {
        EsmTracker.getInstance().traceBackButtonAction(this);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        EsmTracker.getInstance().endSession();
        super.onStop();
    }



}
