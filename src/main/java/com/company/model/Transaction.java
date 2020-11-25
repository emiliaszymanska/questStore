package com.company.model;

import java.time.LocalDate;

public abstract class Transaction {

    private int id;
    private Artifact artifact;
    private LocalDate purchaseDate;

    public Transaction(int id, Artifact artifact, LocalDate purchaseDate) {
        this.id = id;
        this.artifact = artifact;
        this.purchaseDate = purchaseDate;
    }

    public Transaction(Artifact artifact, LocalDate purchaseDate) {
        this.id = 0;
        this.artifact = artifact;
        this.purchaseDate = purchaseDate;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Transaction setId(int id) {
        this.id = id;
        return this;
    }

    public Transaction setArtifact(Artifact artifact) {
        this.artifact = artifact;
        return this;
    }

    public Transaction setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", artifact=" + artifact +
                ", purchaseDate=" + purchaseDate +
                ", ";
    }
}
