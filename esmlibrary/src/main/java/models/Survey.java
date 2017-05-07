package models;

import java.util.ArrayList;

import enums.SurveyType;

/**
 * Created by Cristina on 5/6/2017.
 */

public class Survey {

    private String question;
    private SurveyType type;
    private ArrayList<Answer> answers;
    private boolean hasFreeText;

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public SurveyType getType() {
        return type;
    }

    public void setType(SurveyType type) {
        this.type = type;
    }

    public boolean isHasFreeText() {
        return hasFreeText;
    }

    public void setHasFreeText(boolean hasFreeText) {
        this.hasFreeText = hasFreeText;
    }
}
