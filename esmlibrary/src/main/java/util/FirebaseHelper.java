package util;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import models.firebase.Answer;
import models.firebase.Event;
import models.firebase.Session;
import models.firebase.Survey;
import models.firebase.Task;
import models.firebase.User;
import tracker.EsmTracker;

/**
 * Created by Cristina on 8/23/2017.
 */

public class FirebaseHelper {

    public static void addUser(
            final DatabaseReference firebaseDatabase,
            final User user,
            final String userKey){

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
    }

    public static com.google.android.gms.tasks.Task<Survey> getSurvey(
            final String surveyKey,
            final DatabaseReference databaseReference){

        final TaskCompletionSource<Survey> tcs = new TaskCompletionSource<>();

        final Survey survey = new Survey();
        databaseReference.child("surveys").child(surveyKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Survey dbSurvey = dataSnapshot.getValue(Survey.class);

                        survey.setQuestion(dbSurvey.getQuestion());
                        survey.setAnswers(dbSurvey.getAnswers());

                        for (final String answerKey: dbSurvey.getAnswers().keySet()) {
                            final Answer answer = new Answer();
                            databaseReference.child("answers").child(answerKey)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            answer.setText(
                                                    dataSnapshot.getValue(Answer.class).getText());

                                            survey.getAnswerMap().put(answerKey,answer);

                                            if(survey.getAnswerMap().size()
                                                    == survey.getAnswers().size()){
                                                tcs.setResult(survey);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
        });
        return tcs.getTask();
    }

    public static void addDeviceToken(
            final String deviceToken,
            final Session session,
            final DatabaseReference firebaseDatabase){
        firebaseDatabase.child(Constants.USERS_NODE)
                .child(session.getUserKey())
                .child("token")
                .setValue(deviceToken);
    }

    public static void addNewSession(
            final DatabaseReference firebaseDatabase,
            final Session session,
            final String currentSessionKey){

        Map<String, Object> sessionValues = session.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Constants.SESSIONS_NODE + "/" + currentSessionKey, sessionValues);
        childUpdates.put("/" + Constants.USER_SESSIONS_NODE + "/" + session.getUserKey()
                + "/" + currentSessionKey, sessionValues);

        firebaseDatabase.updateChildren(childUpdates);
    }

    public static void addEventToSession(
            final DatabaseReference firebaseDatabase,
            final Session session,
            final String currentSessionKey){

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Constants.SESSIONS_NODE + "/" + currentSessionKey +
                "/events/" , session.getEvents());
        childUpdates.put("/" + Constants.USER_SESSIONS_NODE + "/" + session.getUserKey()
                + "/" + currentSessionKey + "/events/", session.getEvents());



        firebaseDatabase.updateChildren(childUpdates);
    }

    public static void endSession(
            final DatabaseReference firebaseDatabase,
            final String currentSessionKey){

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/" + Constants.SESSIONS_NODE + "/" + currentSessionKey +
                "/endTime/" , System.currentTimeMillis());
        childUpdates.put("/" + Constants.USER_SESSIONS_NODE + "/"
                + "/" + currentSessionKey + "/endTime/", System.currentTimeMillis());

        firebaseDatabase.updateChildren(childUpdates);
    }

    public static void saveSurvey(Survey survey, DatabaseReference firebaseDatabase){

    }
}
