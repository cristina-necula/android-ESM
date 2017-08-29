package com.esm.android.androidesm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import extensions.EsmBaseActivity;
import tracker.EsmTracker;

public class LoginActivity extends EsmBaseActivity implements View.OnClickListener{

    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EsmTracker.getInstance().startSession(this, "cristinanecula");

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

                if(checkPassword(password) && checkUsername(username)){

                    EsmTracker.getInstance().addButtonClickEvent(this, v.getTag().toString());

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    if(!checkPassword(password)) {
                        EsmTracker.getInstance().addTextError(this, "password_text_view");
                    }
                    if(!checkUsername(username)){
                        EsmTracker.getInstance().addTextError(this, "username_text_view");
                    }
                }
                break;
        }
    }

    public boolean checkPassword(String password){
        return password != null && password != "" && password != " ";
    }

    public boolean checkUsername(String username){
        return username != null && username != "" && username != " ";
    }
}
