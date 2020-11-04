package com.company.model;

import java.util.Date;

public class StudentQuests {

    private Date date;
    private int questId;
    private int studentId;

    public StudentQuests(Date date, int questId, int studentId) {
        this.date = date;
        this.questId = questId;
        this.studentId = studentId;
    }

    public StudentQuests() { }

    public Date getDate() {
        return this.date;
    }

    public StudentQuests setDate(Date date) {
        this.date = date;
        return this;
    }

    public int getQuestId() {
        return this.questId;
    }

    public StudentQuests setQuestId(int questId) {
        this.questId = questId;
        return this;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public StudentQuests setStudentId(int studentId) {
        this.studentId = studentId;
        return this;
    }
}
