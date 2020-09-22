package com.company.model;

import java.util.List;

public class Wallet {

    private int studentId;
    private int balance;
    private List<Transaction> transactionList;

    public Wallet(int studentId, int balance, List<Transaction> transactionList) {
        this.studentId = studentId;
        this.balance = balance;
        this.transactionList = transactionList;
    }

    public Wallet() {
    }

    public int getStudentId() {
        return studentId;
    }
  
    public int getBalance() {
        return balance;
    }

    public List<Transaction> getArtifactList() {
        return transactionList;
    }

    public Wallet setStudentId(int studentId) {
        this.studentId = studentId;
        return this;
    }

    public Wallet setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public Wallet setArtifactList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
        return this;
    }
}
