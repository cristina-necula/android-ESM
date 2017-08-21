package models.firebase;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Task {

    String type;
    String tag;
    String containerActivityName;

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
}
