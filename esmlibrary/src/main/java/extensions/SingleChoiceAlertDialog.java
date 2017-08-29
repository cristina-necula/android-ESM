package extensions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.esm.android.esmlibrary.R;

import models.firebase.Survey;


/**
 * Created by Cristina on 8/20/2017.
 */

public class SingleChoiceAlertDialog extends DialogFragment {

    private AppCompatActivity currentActivity;
    private Survey survey;
    private int selectedAnswerIndex;

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public AppCompatActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(AppCompatActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int which = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(survey.getQuestion())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        survey.setSelectedAnswer(survey.getStringAnswers()[selectedAnswerIndex]);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        survey.setDismissed(true);
                    }
                })
                .setSingleChoiceItems(survey.getStringAnswers(), which, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedAnswerIndex = which;
                    };
                });

        return builder.create();
    }
}
