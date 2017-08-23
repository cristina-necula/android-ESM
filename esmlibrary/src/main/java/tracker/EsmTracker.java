package tracker;

import android.content.Context;
import android.provider.Settings.Secure;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import extensions.EsmBaseActivity;
import models.firebase.Event;
import models.firebase.Location;
import models.firebase.Session;
import models.firebase.User;
import models.firebase.Workflow;
import util.Constants;
import util.FirebaseHelper;

/**
 * Created by Cristina on 5/6/2017.
 */
public class EsmTracker {

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

    public void startSession(Context context, String username){

        String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        final String userKey = username + "-" + deviceId;
        final User user = new User();

        user.setDeviceId(deviceId);
        user.setUsername(username.replace(".", ""));
        FirebaseHelper.addUser(firebaseDatabase, user, userKey);

        session.setStartTime(System.currentTimeMillis());
        session.setUserKey(userKey);
        currentSessionKey = firebaseDatabase.child(Constants.SESSIONS_NODE).push().getKey();

        FirebaseHelper.addNewSession(firebaseDatabase, session, currentSessionKey);
    }

    public void addButtonClickEvent(EsmBaseActivity activity, String buttonTag){
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
        session.getEvents().add(event);
        FirebaseHelper.addEventToSession(firebaseDatabase, session, currentSessionKey);
    }

    public void addActivityStartedEvent(EsmBaseActivity activity){
        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setContainerActivityName(activity.getActivityName());
        event.setType("ActivityStarted");

        activity.requestUserLocation();
        if(activity.LastKnownLocation != null){
            Location location = new Location();
            location.setLatitude(activity.LastKnownLocation.getLatitude());
            location.setLongitude(activity.LastKnownLocation.getLongitude());
            location.setName(activity.getLastLocationName());
            event.setLocation(location);
        }
        session.getEvents().add(event);
        FirebaseHelper.addEventToSession(firebaseDatabase, session, currentSessionKey);
    }

    public void endSession(){
        session.setEndTime(System.currentTimeMillis());
        // TODO - send local data to server
    }

//    public void traceButtonAction(View view){
//        Action action = new ButtonAction();
//        action.Timestamp = System.currentTimeMillis();
//        action.Tag = view.getTag().toString();
//        EsmTracker.getInstance().traceInteraction(action);
//    }
//
//    public void traceBackButtonAction(){
//        Action action = new BackButtonAction();
//        action.Timestamp = System.currentTimeMillis();
//        action.Tag = "BackButtonPressed";
//        EsmTracker.getInstance().traceInteraction(action);
//    }

//    public void traceInteraction(Interaction interaction){
//        session.getUserIntercations().add(interaction);
//
//        Survey survey = shouldTriggerSurvey();
//        if(survey != null){
//            showSurvey(survey);
//        }
//    }
//
//    public Survey shouldTriggerSurvey(){
//        for (Workflow workflow : workflowList) {
//            for (Interaction interaction : workflow.getInteractions()){
//
//            }
//        }
//        return null;
//    }
//
//    private void showSurvey(Survey survey){
//
//    }
}
