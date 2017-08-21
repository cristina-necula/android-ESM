package models.firebase;

import java.util.ArrayList;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Workflow {

    ArrayList<Task> tasks;
    Survey survey;

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
