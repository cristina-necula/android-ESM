package models.Event;

import java.util.UUID;

import enums.EventType;

/**
 * Created by Cristina on 8/15/2017.
 */

public class ActivityOpenedEvent extends Event {

    public ActivityOpenedEvent() {
        Type = EventType.ActivityOpened;
        Uuid = UUID.randomUUID();
    }
}
