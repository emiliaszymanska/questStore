package com.company.model;

import java.time.LocalDate;

public class SingleTransaction extends Transaction {

    private Payment payment;

    public SingleTransaction(int id, Artifact artifact, LocalDate purchaseDate, Payment payment) {
        super(id, artifact, purchaseDate);
        this.payment = payment;
    }

    public SingleTransaction(Artifact artifact, LocalDate purchaseDate, Payment payment) {
        super(artifact, purchaseDate);
        this.payment = payment;
    }

    public SingleTransaction() {
    }

    public SingleTransaction setPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public Payment getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return super.toString() +
                "payment=" + payment +
                '}';
    }
}
