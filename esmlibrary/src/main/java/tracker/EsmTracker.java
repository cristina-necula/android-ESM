package tracker;

import android.content.Context;
import android.provider.Settings.Secure;
import android.view.View;

import java.util.ArrayList;
import java.util.UUID;

import Interfaces.Interaction;
import models.Action.Action;
import models.Action.BackButtonAction;
import models.Action.ButtonAction;
import models.survey.Survey;

/**
 * Created by Cristina on 5/6/2017.
 */
public class EsmTracker {

    private static EsmTracker instance = new EsmTracker();

    private Session session;

    private ArrayList<Workflow> workflowList;

    public ArrayList<Workflow> getWorkflowList() {
        return workflowList;
    }

    public void setWorkflowList(ArrayList<Workflow> workflows) {
        this.workflowList = workflows;
    }

    private EsmTracker() {
        session = new Session();
    }

    public static EsmTracker getInstance() {
        return instance;
    }

    public void startSession(Context context, String userId){
        session.setStartTime(System.currentTimeMillis());
        session.setId(UUID.randomUUID());
        session.setDeviceId(Secure.getString(context.getContentResolver(), Secure.ANDROID_ID));
        session.setUserId(userId);
    }

    public void endSession(){
        session.setEndTime(System.currentTimeMillis());
        // TODO - send local data to server
    }

    public void traceButtonAction(View view){
        Action action = new ButtonAction();
        action.Timestamp = System.currentTimeMillis();
        action.Tag = view.getTag().toString();
        EsmTracker.getInstance().traceInteraction(action);
    }

    public void traceBackButtonAction(){
        Action action = new BackButtonAction();
        action.Timestamp = System.currentTimeMillis();
        action.Tag = "BackButtonPressed";
        EsmTracker.getInstance().traceInteraction(action);
    }

    public void traceInteraction(Interaction interaction){
        session.getUserIntercations().add(interaction);

        Survey survey = shouldTriggerSurvey();
        if(survey != null){
            showSurvey(survey);
        }
    }

    public Survey shouldTriggerSurvey(){
        for (Workflow workflow : workflowList) {
            for (Interaction interaction : workflow.getInteractions()){

            }
        }
        return null;
    }

    private void showSurvey(Survey survey){

    }
}
