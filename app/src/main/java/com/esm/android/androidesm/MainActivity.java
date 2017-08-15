package com.esm.android.androidesm;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Random;

import extensions.EsmBaseActivity;
import tracker.EsmTracker;

public class MainActivity extends EsmBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EsmTracker.getInstance().startSession(getApplicationContext(), "TODO_userId");

    }

    @Override
    public void onClick(View view) {

    }


}
