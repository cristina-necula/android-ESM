package com.esm.android.androidesm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Interfaces.Interaction;
import extensions.EsmBaseActivity;
import tracker.EsmTracker;

public class LoginActivity extends EsmBaseActivity implements View.OnClickListener{

    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        requestUserLocation();
        requestRecognitionOfUserActivity(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                String username = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                if(username != null && username != "" && username != " " ||
                        password != null && password != "" && password != " "){
                    EsmTracker.getInstance().startSession(this, username);
                    EsmTracker.getInstance().addActivityStartedEvent(this);
                    EsmTracker.getInstance().addButtonClickEvent(this, v.getTag().toString());

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Fill in username and password", Toast.LENGTH_SHORT);
                }
                break;
        }
    }
}
