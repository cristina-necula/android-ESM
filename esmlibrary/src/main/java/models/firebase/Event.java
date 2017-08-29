package models.firebase;

import android.location.*;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cristina on 8/21/2017.
 */
@IgnoreExtraProperties
public class Event {

    long timestamp;
    String type;
    String tag;
    String containerActivityName;
    String detectedUserActivity;
    Location location;
    boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContainerActivityName() {
        return containerActivityName;
    }

    public void setContainerActivityName(String containerActivityName) {
        this.containerActivityName = containerActivityName;
    }

    public String getDetectedUserActivity() {
        return detectedUserActivity;
    }

    public void setDetectedUserActivity(String detectedUserActivity) {
        this.detectedUserActivity = detectedUserActivity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("timestamp", timestamp);
        map.put("type", type);
        map.put("tag", tag);
        map.put("containerActivityName", containerActivityName);
        map.put("detectedUserActivity", detectedUserActivity);
        map.put("location", location);
        map.put("error", error);

        return map;
    }
}
