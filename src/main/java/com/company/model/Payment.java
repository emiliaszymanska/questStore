package com.company.model;

import com.company.model.user.Student;

import java.time.LocalDate;

public class Payment {

    private LocalDate paymentDate;
    private int amount;
    private Student student;

    public Payment() {
    }

    public Payment(LocalDate paymentDate, int amount, Student student) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.student = student;
    }

    public Payment setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public Payment setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public Payment setStudent(Student student) {
        this.student = student;
        return this;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public int getAmount() {
        return amount;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "student=" + student.getFirstName() + " " + student.getLastName() +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}
