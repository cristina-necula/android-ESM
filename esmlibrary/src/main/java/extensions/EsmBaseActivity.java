package extensions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;
import android.view.View;

import com.esm.android.esmlibrary.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import util.Constants;


/**
 * Created by Cristina on 8/13/2017.
 */

public class EsmBaseActivity extends AppCompatActivity {

    public FusedLocationProviderClient FusedLocationClient;
    public Location LastKnownLocation;
    public GoogleApiClient GoogleApiClient;

    public int MainActivityContainerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void requestUserLocation(){
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }

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
                            showSnackbar("No location detected");
                        }
                    }
                });
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
}
