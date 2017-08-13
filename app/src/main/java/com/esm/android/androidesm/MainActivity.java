package com.esm.android.androidesm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tracker.EsmTracker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



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
