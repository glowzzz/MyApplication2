package com.example.myapplication;

public class Question {
    private int mtextId;
    private boolean mAnswer;
    public int getMtextId() {
        return mtextId;
    }

    public Question(int mtextId, boolean answer) {
        this.mtextId = mtextId;
        mAnswer = answer;
    }

    public void setMtextId(int mtextId) {
        this.mtextId = mtextId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
