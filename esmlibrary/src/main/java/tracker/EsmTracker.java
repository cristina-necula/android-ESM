package tracker;

import android.content.Context;
import android.provider.Settings.Secure;

import java.util.UUID;

import models.Session;

/**
 * Created by Cristina on 5/6/2017.
 */
public class EsmTracker {

    private static EsmTracker instance = new EsmTracker();

    private Session session;

    public static EsmTracker getInstance() {
        return instance;
    }

    private EsmTracker() {
        session = new Session();
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
}
