package util;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import models.firebase.Event;
import models.firebase.Session;
import models.firebase.User;

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
}
