package com.company.service;

import com.company.dao.ArtifactDao;
import com.company.dao.ArtifactTypeDao;
import com.company.dao.TransactionDao;
import com.company.dao.UserDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.*;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final ArtifactDao artifactDao;
    private ArtifactTypeDao artifactTypeDao;
    private final UserDao userDao;
    private final ObjectMapper mapper;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
        this.artifactDao = new ArtifactDao();
        this.artifactTypeDao = new ArtifactTypeDao();
        this.userDao = new UserDao();
        this.mapper = new ObjectMapper();
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

    public List<Transaction> getNotFinishedGroupTransactions() throws ObjectNotFoundException {
        List<Transaction> groupTransactions = transactionDao.getGroupTransactions();
        List<Transaction> notFinishedGroupTransactions = new ArrayList<>();
        for (Transaction transaction : groupTransactions) {
            if (transaction.getPurchaseDate() == null) {
                notFinishedGroupTransactions.add(transaction);
            }
        }
        System.out.println(notFinishedGroupTransactions.toString());
        return notFinishedGroupTransactions;
    }

    public void insertTransaction(Map<String, String> formData) throws ObjectNotFoundException {
        Transaction transaction = createTransaction(formData);
        transactionDao.insertTransaction(transaction);
    }

    public void insertPaymentToGroupBuying(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException {
        User user = userDao.getBySessionId(uuid);
        Transaction transaction = createTransaction(formData);
        int groupTransactionId = Integer.parseInt(formData.get("group_transaction_id"));
        int amount = Integer.parseInt(formData.get("price"));
        ((Student) user).setBalance(((Student) user).getBalance() - amount);
        userDao.update(user);
        transactionDao.insertIntoGroupBuying((GroupTransaction) transaction, groupTransactionId);
        checkIfTransactionFinished(groupTransactionId);
    }

    public String buyArtifact(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        Artifact artifact = artifactDao.getById(Integer.parseInt(formData.get("artifact_id")));
        User user = userDao.getBySessionId(uuid);
        Transaction transaction;
        if (artifact.isGroup()) {
            transaction = new GroupTransaction(artifact, null,
                                                List.of(new Payment(LocalDate.now(),
                                                        Integer.parseInt(formData.get("price")), (Student) user)));
            ((Student) user).setBalance(((Student) user).getBalance() - Integer.parseInt(formData.get("price")));
            userDao.update(user);
        } else {
            transaction = new SingleTransaction(artifact, LocalDate.now(), new Payment(LocalDate.now(),
                                                artifact.getPrice(), (Student) user));
            ((Student) user).setBalance(((Student) user).getBalance() - artifact.getPrice());
            userDao.update(user);
        }
        transactionDao.insertTransaction(transaction);
        return mapper.writeValueAsString(transaction);
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

    private Transaction createTransaction(Map<String, String> formData) throws ObjectNotFoundException {
        Artifact artifact = new Artifact();
        artifact.setId(Integer.parseInt(formData.get("artifact_id")))
                .setName(formData.get("name"))
                .setDescription(formData.get("description"))
                .setPrice(Integer.parseInt(formData.get("price")))
                .setType(artifactTypeDao.getTypeById(Integer.parseInt(formData.get("artifact_type_id"))))
                .setGroup(Boolean.parseBoolean(formData.get("is_group")));

        LocalDate purchaseDate = LocalDate.parse(formData.get("purchase_date"));

        if (artifact.isGroup()) {
            List<Payment> paymentList = List.of(new Payment(LocalDate.now(), Integer.parseInt(formData.get("price")),
                                            (Student) userDao.getById(Integer.parseInt(formData.get("student_id")))));
            Transaction groupTransaction = new GroupTransaction(Integer.parseInt(formData.get("bought_artifact_id")),
                    artifact,
                    purchaseDate,
                    paymentList);

            return groupTransaction;

        } else {
            Payment payment = new Payment(purchaseDate,
                    Integer.parseInt(formData.get("price")),
                    (Student) userDao.getById(Integer.parseInt(formData.get("student_id"))));

            Transaction singleTransaction = new SingleTransaction(Integer.parseInt(formData.get("bought_artifact_id")),
                    artifact,
                    purchaseDate,
                    payment);

            return singleTransaction;
        }
    }
}
