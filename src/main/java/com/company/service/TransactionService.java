package com.company.service;

import com.company.dao.*;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.*;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final ArtifactDao artifactDao;
    private final UserDao userDao;
    private final StudentDao studentDao;
    private final ObjectMapper mapper;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
        this.artifactDao = new ArtifactDao();
        this.userDao = new UserDao();
        this.studentDao = new StudentDao();
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

    public String getNotFinishedGroupTransactions() throws ObjectNotFoundException, JsonProcessingException {
        List<Transaction> groupTransactions = transactionDao.getGroupTransactions();
        List<Transaction> notFinishedGroupTransactions = new ArrayList<>();
        for (Transaction transaction : groupTransactions) {
            if (transaction.getPurchaseDate() == null) {
                notFinishedGroupTransactions.add(transaction);
            }
        }
        System.out.println(notFinishedGroupTransactions.toString());
        return mapper.writeValueAsString(notFinishedGroupTransactions);
    }

    public void insertTransaction(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException {
        Transaction transaction = createTransaction(formData, uuid);
        transactionDao.insertTransaction(transaction);
    }

    public String insertPaymentToGroupBuying(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        Student user = (Student) studentDao.getBySessionId(uuid);
        Student student = studentDao.getStudentByIdWithAdditionalData(user.getId());
        if (!calculateStudentBalance(student, Integer.parseInt(formData.get("amount")))) {
            return "You haven't got enough money";
        }
        studentDao.updateAdditionalStudentData(student);

        Transaction transaction = createTransaction(formData, uuid);
        int groupTransactionId = Integer.parseInt(formData.get("bought_artifact_id"));
        transactionDao.insertIntoGroupBuying((GroupTransaction) transaction, groupTransactionId);
        checkIfTransactionFinished(groupTransactionId);
        return mapper.writeValueAsString(transaction);
    }

    public String buyArtifact(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        Artifact artifact = artifactDao.getById(Integer.parseInt(formData.get("artifact_id")));
        Student user = (Student) studentDao.getBySessionId(uuid);
        Student student = studentDao.getStudentByIdWithAdditionalData(user.getId());
        Transaction transaction;
        if (artifact.isGroup()) {
            transaction = new GroupTransaction(artifact, null,
                                                List.of(new Payment(LocalDate.now(),
                                                        Integer.parseInt(formData.get("amount")), student)));
            if (!calculateStudentBalance(student, Integer.parseInt(formData.get("amount")))) {
                return "You haven't got enough money";
            }
            studentDao.updateAdditionalStudentData(student);
        } else {
            transaction = new SingleTransaction(artifact, LocalDate.now(), new Payment(LocalDate.now(),
                                                artifact.getPrice(), student));
            if (!calculateStudentBalance(student, artifact.getPrice())) {
                return "You haven't got enough money";
            }
            studentDao.updateAdditionalStudentData(student);
        }
        transactionDao.insertTransaction(transaction);
        return mapper.writeValueAsString(transaction);
    }

    private boolean calculateStudentBalance(Student student, int amountToSubtract) {
        int studentBalance = student.getBalance();
        if (studentBalance >= amountToSubtract) {
            student.setBalance(studentBalance - amountToSubtract);
            return true;
        }
        return false;
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

    private Transaction createTransaction(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException {
        Artifact artifact = artifactDao.getById(Integer.parseInt(formData.get("artifact_id")));

        LocalDate purchaseDate = LocalDate.now();

        if (artifact.isGroup()) {
            List<Payment> paymentList = createPayments(formData, uuid);
            Transaction groupTransaction = new GroupTransaction(
                    artifact,
                    null,
                    paymentList);

            return groupTransaction;

        } else {
            Payment payment = new Payment(purchaseDate,
                    artifact.getPrice(),
                    (Student) userDao.getBySessionId(uuid));

            Transaction singleTransaction = new SingleTransaction(
                    artifact,
                    purchaseDate,
                    payment);

            return singleTransaction;
        }
    }

    private List<Payment> createPayments(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException {
        Payment payment = new Payment(LocalDate.now(), Integer.parseInt(formData.get("amount")),
                                    (Student) userDao.getBySessionId(uuid));
        return List.of(payment);
    }
}
