package extensions;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import models.firebase.Answer;
import models.firebase.Survey;


/**
 * Created by Cristina on 8/20/2017.
 */

public class MultipleChoiceAlertDialog extends DialogFragment {

    private AppCompatActivity currentActivity;
    private Survey survey;
    private ArrayList<Answer> selectedAnswers;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] surveyStringAnswers = survey.getStringAnswers();

        builder.setTitle(survey.getQuestion())
                .setMultiChoiceItems(surveyStringAnswers, null,
                        new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            survey.setSelectedAnswer(surveyStringAnswers[which], false);
                        }
                        else {
                            survey.setSelectedAnswer(surveyStringAnswers[which], true);
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        survey.setDismissed(false);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        survey.setDismissed(true);
                    }
                });
        return builder.create();
    }

}
