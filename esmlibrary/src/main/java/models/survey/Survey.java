package models.survey;

import java.util.ArrayList;

import enums.SurveyType;
import models.survey.Answer;

/**
 * Created by Cristina on 5/6/2017.
 */

public class Survey {

    private String id;
    private String question;
    private SurveyType type;
    private ArrayList<Answer> answers;
    private String[] stringAnswers;
    private boolean dismissed;

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public String[] getStringAnswers() {
        return stringAnswers;
    }

    public void setStringAnswers(String[] stringAnswers) {
        this.stringAnswers = stringAnswers;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {

        this.answers = answers;
        stringAnswers = new String[answers.size()];
        int i = 0;
        for (Answer answer : answers) {
            stringAnswers[i] = answer.getText();
            i++;
        }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSelectedAnswer(int index){
        Answer selected = answers.get(index);
        selected.setSelected(true);
    }

    public void setSelectedAnswers(ArrayList<Answer> selectedAnswers){
        for (Answer selectedAnswer : selectedAnswers) {
            for(Answer answer : answers){
                if(selectedAnswer.getText().equals(answer.getText())){
                    answer.setSelected(true);
                }
            }
        }
    }
}
