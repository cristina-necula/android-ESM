package tracker;

import java.util.LinkedList;

import Interfaces.Interaction;
import models.Action.Action;

/**
 * Created by Cristina on 8/6/2017.
 */

public class Workflow {
    private LinkedList<Interaction> actions;

    public LinkedList<Interaction> getActions() {
        return actions;
    }

    public void setActions(LinkedList<Interaction> actions) {
        this.actions = actions;
    }
}
