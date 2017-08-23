package tracker;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import models.firebase.Session;
import models.firebase.User;
import util.Constants;

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

    public void startSession(Context context, String userEmail){

        String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        final User user = new User();
        user.setDeviceId(deviceId);
        user.setEmail(userEmail);

        final String userKey = userEmail + "-" + deviceId;

        // add user to db if it does not exist
        firebaseDatabase.child(Constants.USERS_NODE).child(userKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    firebaseDatabase.child(Constants.USERS_NODE)
                            .child(userKey)
                            .setValue(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("Firebase error", firebaseError.getMessage());
            }
        });

        session.setStartTime(System.currentTimeMillis());
        session.setUserKey(userKey);

        currentSessionKey = firebaseDatabase.child(Constants.SESSIONS_NODE).push().getKey();
        Map<String, Object> sessionValues = session.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Constants.SESSIONS_NODE + "/" + currentSessionKey, sessionValues);
        childUpdates.put("/" + Constants.USER_SESSIONS_NODE + "/" + session.getUserKey()
                + "/" + currentSessionKey, sessionValues);

        firebaseDatabase.updateChildren(childUpdates);
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
