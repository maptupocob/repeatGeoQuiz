package com.martirosov.sergey.repeatgeoquiz;

public class Question {
    private int textResId;
    private boolean isAnswerTrue;

    public int getTextResId() {
        return textResId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public Question(int textResId, boolean isAnswerTrue) {
        this.textResId = textResId;
        this.isAnswerTrue = isAnswerTrue;
    }
}
