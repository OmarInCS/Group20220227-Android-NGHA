package com.alkhalij.guessword;

import java.util.Date;

public class HistoryItem {
    private Date dateTime;
    private int score;

    public HistoryItem(Date dateTime, int score) {
        this.dateTime = dateTime;
        this.score = score;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "HistoryItem{" +
                "dateTime=" + dateTime +
                ", score=" + score +
                '}';
    }
}
