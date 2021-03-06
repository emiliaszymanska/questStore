package com.company.model;

import java.util.List;

public class Wallet {

    private int studentId;
    private int balance;
    private List<Transaction> transactions;

    public Wallet(int studentId, int balance, List<Transaction> transactions) {
        this.studentId = studentId;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Wallet() {
    }

    public int getStudentId() {
        return studentId;
    }
  
    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Wallet setStudentId(int studentId) {
        this.studentId = studentId;
        return this;
    }

    public Wallet setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public Wallet setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }
}
