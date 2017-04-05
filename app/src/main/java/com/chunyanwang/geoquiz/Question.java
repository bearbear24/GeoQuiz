package com.chunyanwang.geoquiz;

/**
 * Created by chunyanwang on 4/3/17.
 */

public class Question {

    private int textResId;
    private boolean answerTrue;

    public Question(int textResId, boolean answerTrue) {
        this.textResId = textResId;
        this.answerTrue = answerTrue;
    }

    public int getTextResId() {
        return textResId;
    }

    public boolean getAnswerTrue() {
        return answerTrue;
    }
}
