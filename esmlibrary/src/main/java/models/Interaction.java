package models;

import android.location.Location;

import java.util.UUID;

import enums.InteractionType;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Interaction implements Interfaces.Interaction {

    public InteractionType Type;

    public Location Location;

    public String Tag;

    public String ContainerActivityName;

    public String DetectedActivity;

    public long Timestamp;

    public UUID Uuid;
}
