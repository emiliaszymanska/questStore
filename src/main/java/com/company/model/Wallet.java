package com.company.model;

import java.util.List;

public class Wallet {

    private int studentId;
    private int balance;
    private List<Artifact> artifactList;

    public Wallet(int studentId, int balance, List<Artifact> artifactList) {
        this.studentId = studentId;
        this.balance = balance;
        this.artifactList = artifactList;
    }

    public Wallet() {
    }

    public int getStudentId() {
        return studentId;
    }
  
    public int getBalance() {
        return balance;
    }

    public List<Artifact> getArtifactList() {
        return artifactList;
    }

    public Wallet setStudentId(int studentId) {
        this.studentId = studentId;
        return this;
    }

    public Wallet setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public Wallet setArtifactList(List<Artifact> artifactList) {
        this.artifactList = artifactList;
        return this;
    }
}
