package tracker;

import java.util.LinkedList;
import java.util.UUID;

import Interfaces.Interaction;

/**
 * Created by Cristina on 5/6/2017.
 */

public class Session {

    private UUID id;
    private String userId;
    private String deviceId;
    private long endTime;
    private long startTime;
    private LinkedList<Interaction> userIntercations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public LinkedList<Interaction> getUserIntercations() {
        return userIntercations;
    }

    public void setUserIntercations(LinkedList<Interaction> userIntercations) {
        this.userIntercations = userIntercations;
    }

    public Session(){
        userIntercations = new LinkedList<>();
    }
}
