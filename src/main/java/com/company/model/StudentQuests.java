package com.company.model;

import com.company.model.user.Student;

import java.time.LocalDate;

public class StudentQuests {

    private LocalDate date;
    private Quest quest;
    private Student student;

    public StudentQuests(LocalDate date, Quest quest, Student student) {
        this.date = date;
        this.quest = quest;
        this.student = student;
    }

    public StudentQuests() { }

    public LocalDate getDate() {
        return this.date;
    }

    public StudentQuests setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Quest getQuest() {
        return this.quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String toString() {
        String quest = getDate() + " " + getQuest() + " " + getStudent();
        System.out.println(quest);
        return quest;
    }
}
