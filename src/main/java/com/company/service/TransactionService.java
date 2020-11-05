package com.company.service;

import com.company.dao.TransactionDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.GroupTransaction;
import com.company.model.Payment;
import com.company.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
    }

    public List<Transaction> getTransactionsByStudentId(int studentId) throws ObjectNotFoundException {
        return transactionDao.getTransactionsByStudentId(studentId);
    }

    public List<Transaction> getTransactionsByArtifactId(int artifactId) throws ObjectNotFoundException {
        return transactionDao.getTransactionsByArtifactId(artifactId);
    }

    public List<Transaction> getTransactionsByBoughtArtifactId(int boughtArtifactId) throws ObjectNotFoundException {
        return transactionDao.getTransactionsByBoughtArtifactId(boughtArtifactId);
    }

    public void insertTransaction(Transaction transaction) throws ObjectNotFoundException {
        transactionDao.insertTransaction(transaction);
    }

    public void insertPaymentToGroupBuying(GroupTransaction transaction, int groupTransactionId) throws ObjectNotFoundException {
        transactionDao.insertIntoGroupBuying(transaction, groupTransactionId);
        checkIfTransactionFinished(groupTransactionId);
    }

    private void checkIfTransactionFinished(int groupTransactionId) throws ObjectNotFoundException {
        List<Transaction> transactions = transactionDao.getTransactionsByBoughtArtifactId(groupTransactionId);
        for (Transaction transaction : transactions) {
            if (transaction instanceof GroupTransaction) {
                List<Payment> payments = ((GroupTransaction) transaction).getPayments();
                int artifactPrice = transaction.getArtifact().getPrice();
                int sumOfPayments = 0;
                for (Payment payment : payments) {
                    sumOfPayments += payment.getAmount();
                }
                if (sumOfPayments == artifactPrice) {
                    transactionDao.updatePurchaseDate(LocalDate.now(), groupTransactionId);
                }
            }
        }
    }
}
