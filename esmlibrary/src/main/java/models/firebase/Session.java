package models.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cristina on 8/21/2017.
 */
@IgnoreExtraProperties
public class Session {

    String userKey;
    long startTime;
    long endTime;
    ArrayList<Event> events;

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

    public ArrayList<Event> getEvents() {
        if(events == null){
            events = new ArrayList<>();
        }
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Session(){ }

    @Exclude
    public Map<String, Object> toMap(){

        HashMap<String, Object> map = new HashMap<>();

        map.put("userKey", userKey);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("events", events);

        return map;
    }
}
