package com.company.model;

import java.time.LocalDate;
import java.util.List;

public class GroupTransaction extends Transaction {

    private List<Payment> payments;

    public GroupTransaction() {
        super();
    }

    public GroupTransaction(int id, Artifact artifact, LocalDate purchaseDate, List<Payment> payments) {
        super(id, artifact, purchaseDate);
        this.payments = payments;
    }

    public GroupTransaction setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() +
                "payments=" + payments +
                '}';
    }
}
