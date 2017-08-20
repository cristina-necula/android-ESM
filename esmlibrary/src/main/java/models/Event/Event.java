package models.Event;

import java.util.UUID;

import enums.EventType;
import Interfaces.Interaction;

/**
 * Created by Cristina on 5/6/2017.
 */

public abstract class Event implements Interaction {

    public EventType Type;
    public UUID Uuid;

}
