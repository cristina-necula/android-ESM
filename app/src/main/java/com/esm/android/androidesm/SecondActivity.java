package com.esm.android.androidesm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import extensions.EsmBaseActivity;
import tracker.EsmTracker;

public class SecondActivity extends EsmBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }
}
