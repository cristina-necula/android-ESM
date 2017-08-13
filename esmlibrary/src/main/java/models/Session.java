package models;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Cristina on 5/6/2017.
 */

public class Session {

    private UUID id;
    private String userId;
    private String deviceId;
    private long endTime;
    private long startTime;
    private LinkedList<Action> userActions;

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

    public LinkedList<Action> getUserActions() {
        return userActions;
    }

    public void setUserActions(LinkedList<Action> userActions) {
        this.userActions = userActions;
    }

    public Session(){
        userActions = new LinkedList<>();
    }
}
