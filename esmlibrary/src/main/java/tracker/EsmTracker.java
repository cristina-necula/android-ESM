package tracker;

import android.content.Context;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import extensions.EsmBaseActivity;
import extensions.SingleChoiceAlertDialog;
import models.firebase.Event;
import models.firebase.Location;
import models.firebase.Session;
import models.firebase.Survey;
import models.firebase.User;
import models.firebase.Workflow;
import util.Constants;
import util.FirebaseHelper;

/**
 * Created by Cristina on 5/6/2017.
 */
public class EsmTracker {

    private boolean activeFlag;

    private EsmBaseActivity currentActivity;

    private String currentWorkflowKey;

    private static EsmTracker instance = new EsmTracker();

    private Session session;

    private DatabaseReference firebaseDatabase;

    private String currentSessionKey;

    private ArrayList<Workflow> workflowList;

    public ArrayList<Workflow> getWorkflowList() {
        return workflowList;
    }

    public void setWorkflowList(ArrayList<Workflow> workflows) {
        this.workflowList = workflows;
    }

    private EsmTracker() {
        session = new Session();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static EsmTracker getInstance() {
        return instance;
    }

    public void startSession(EsmBaseActivity activity, String username){

        currentActivity = activity;

        if(activeFlag){
            return;
        }

        activeFlag = true;

        String deviceId = Secure.getString(activity.getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
        final String userKey = username + "-" + deviceId;
        final User user = new User();

        user.setDeviceId(deviceId);
        user.setUsername(username.replace(".", ""));
        user.setToken(FirebaseInstanceId.getInstance().getToken());
        FirebaseHelper.addUser(firebaseDatabase, user, userKey);

        session.setStartTime(System.currentTimeMillis());
        session.setUserKey(userKey);
        currentSessionKey = firebaseDatabase.child(Constants.SESSIONS_NODE).push().getKey();

        FirebaseHelper.addNewSession(firebaseDatabase, session, currentSessionKey);
    }

    public void addButtonClickEvent(EsmBaseActivity activity, String buttonTag){

        currentActivity = activity;

        Event event = new Event();
        event.setTag(buttonTag);
        event.setTimestamp(System.currentTimeMillis());
        event.setContainerActivityName(activity.getActivityName());
        event.setType("ButtonClick");

        activity.requestUserLocation();
        if(activity.LastKnownLocation != null){
            Location location = new Location();
            location.setLatitude(activity.LastKnownLocation.getLatitude());
            location.setLongitude(activity.LastKnownLocation.getLongitude());
            location.setName(activity.getLastLocationName());
            event.setLocation(location);
        }
        session.addEvent(event);
        FirebaseHelper.addEventToSession(firebaseDatabase, session, currentSessionKey);
    }

    public void showSurvey(String surveyKey, String workflowKey){
        currentWorkflowKey = workflowKey;
        Task<Survey> surveyTask = FirebaseHelper.getSurvey(surveyKey, firebaseDatabase);

        surveyTask.addOnCompleteListener(new OnCompleteListener<Survey>() {
            @Override
            public void onComplete(@NonNull Task<Survey> task) {
                SingleChoiceAlertDialog singleChoiceAlertDialog = new SingleChoiceAlertDialog();
                singleChoiceAlertDialog.setSurvey(task.getResult());
                singleChoiceAlertDialog.setCurrentActivity(currentActivity);
                singleChoiceAlertDialog.show(currentActivity.getSupportFragmentManager(), "");
            }
        });
    }

    public void addActivityStartedEvent(EsmBaseActivity activity){

        currentActivity = activity;

        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setContainerActivityName(activity.getActivityName());
        event.setType("ActivityStarted");
        event.setTag("Opened");

        activity.requestUserLocation();
        if(activity.LastKnownLocation != null){
            Location location = new Location();
            location.setLatitude(activity.LastKnownLocation.getLatitude());
            location.setLongitude(activity.LastKnownLocation.getLongitude());
            location.setName(activity.getLastLocationName());
            event.setLocation(location);
        }
        session.addEvent(event);
        FirebaseHelper.addEventToSession(firebaseDatabase, session, currentSessionKey);
    }

    public void onTokenRefresh(String token){
        FirebaseHelper.addDeviceToken(token, session, firebaseDatabase);
    }

    public void endSession(){
        session.setEndTime(System.currentTimeMillis());
        FirebaseHelper.endSession(firebaseDatabase, currentSessionKey);
    }

    public void traceBackButtonAction(EsmBaseActivity activity) {

        currentActivity = activity;

        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setContainerActivityName(activity.getActivityName());
        event.setType("BackButtonPressed");
        event.setTag("BackButton");

        activity.requestUserLocation();
        if(activity.LastKnownLocation != null){
            Location location = new Location();
            location.setLatitude(activity.LastKnownLocation.getLatitude());
            location.setLongitude(activity.LastKnownLocation.getLongitude());
            location.setName(activity.getLastLocationName());
            event.setLocation(location);
        }
        session.addEvent(event);
        FirebaseHelper.addEventToSession(firebaseDatabase, session, currentSessionKey);
    }

    public void saveSurvey(Survey survey){

    }

    public void addTextError(EsmBaseActivity activity, String tag){
        currentActivity = activity;

        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setContainerActivityName(activity.getActivityName());
        event.setType("Error");
        event.setTag(tag);

        activity.requestUserLocation();
        if(activity.LastKnownLocation != null){
            Location location = new Location();
            location.setLatitude(activity.LastKnownLocation.getLatitude());
            location.setLongitude(activity.LastKnownLocation.getLongitude());
            location.setName(activity.getLastLocationName());
            event.setLocation(location);
        }
        session.addEvent(event);
        FirebaseHelper.addEventToSession(firebaseDatabase, session, currentSessionKey);
    }
}
