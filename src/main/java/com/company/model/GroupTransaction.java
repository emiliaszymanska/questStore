package com.company.model;

import java.time.LocalDate;
import java.util.List;

public class GroupTransaction extends Transaction {

    private List<Payment> payments;

    public GroupTransaction(int id, Artifact artifact, LocalDate purchaseDate, List<Payment> payments) {
        super(id, artifact, purchaseDate);
        this.payments = payments;
    }

    public GroupTransaction(Artifact artifact, LocalDate purchaseDate, List<Payment> payments) {
        super(artifact, purchaseDate);
        this.payments = payments;
    }

    public GroupTransaction() {
        super();
    }

    public GroupTransaction setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    @Override
    public String toString() {
        return super.toString() +
                "payments=" + payments +
                '}';
    }
}
