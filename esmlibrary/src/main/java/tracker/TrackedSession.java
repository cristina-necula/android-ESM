package tracker;

import java.util.LinkedList;

import Interfaces.Interaction;
import models.survey.Survey;

/**
 * Created by Cristina on 8/20/2017.
 */

public class TrackedSession {

    private LinkedList<Interaction> interactions;
    private Survey survey;

    public LinkedList<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(LinkedList<Interaction> interactions) {
        this.interactions = interactions;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
