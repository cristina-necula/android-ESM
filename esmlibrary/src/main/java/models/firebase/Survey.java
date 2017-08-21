package models.firebase;

import java.util.Map;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Survey {

    String question;
    boolean isSingleChoice;
    Map<String, Answer> answerMap;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isSingleChoice() {
        return isSingleChoice;
    }

    public void setSingleChoice(boolean singleChoice) {
        isSingleChoice = singleChoice;
    }

    public Map<String, Answer> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<String, Answer> answerMap) {
        this.answerMap = answerMap;
    }
}
