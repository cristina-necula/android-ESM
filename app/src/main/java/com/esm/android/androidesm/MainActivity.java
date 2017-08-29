package com.esm.android.androidesm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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



        Button button = (Button)findViewById(R.id.go_to_second_activity);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_to_second_activity:
                EsmTracker.getInstance().addButtonClickEvent(this, view.getTag().toString());
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
        }
    }


}
