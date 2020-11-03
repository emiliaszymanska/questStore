package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;
import com.company.model.*;
import com.company.model.user.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    private final String selectTransactions = "SELECT ba.purchase_date, ba.id AS bought_artifact_id,"
            + " ba.artifact_id AS bought_artifact_artifact_id, ba.student_id AS student_id, a.id AS artifact_id, a.name,"
            + " a.description, a.price, a.artifact_type_id, a.is_group"
            + " FROM bought_artifacts AS ba"
            + " INNER JOIN artifacts AS a ON ba.artifact_id = a.id ";
    private final String insertTransaction = "";
    private final String byStudentId = "WHERE student_id = ?;";
    private final String byArtifactId = "WHERE artifact_id = ?;";
    private final Connector CONNECTOR;
    private ArtifactTypeDao artifactTypeDao;
    private UserDao userDao;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public TransactionDao() {
        this.CONNECTOR = new Connector();
        this.artifactTypeDao = new ArtifactTypeDao();
        this.userDao = new UserDao();
    }

    public List<Transaction> getTransactionsByStudentId(int studentId) throws ObjectNotFoundException {
        return getTransactionsById(selectTransactions + byStudentId, studentId);
    }

    public List<Transaction> getTransactionByArtifactId(int artifactId) throws ObjectNotFoundException {
        return getTransactionsById(selectTransactions + byArtifactId, artifactId);
    }

    private List<Transaction> getTransactionsById(String query, int id) throws ObjectNotFoundException {
        List<Transaction> transactions = new ArrayList<>();

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                createTransaction(resultSet, transactions);
                System.out.println(transactions);
            }
            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found");
        }

        return transactions;
    }

    private void createTransaction(ResultSet resultSet, List<Transaction> transactions) throws SQLException {
        Artifact artifact = new Artifact();
        artifact.setId(resultSet.getInt("artifact_id"))
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setPrice(resultSet.getInt("price"))
                .setType(artifactTypeDao.getTypeById(resultSet.getInt("artifact_type_id")))
                .setGroup(resultSet.getBoolean("is_group"));

        LocalDate purchaseDate = LocalDate.parse(resultSet.getString("purchase_date"));

        if (artifact.isGroup()) {
            List<Payment> paymentList = createPaymentList(resultSet.getInt("bought_artifact_id"));
            Transaction groupTransaction = new GroupTransaction(resultSet.getInt("bought_artifact_id"),
                    artifact,
                    purchaseDate,
                    paymentList);

            transactions.add(groupTransaction);

        } else {
            Payment payment = new Payment(purchaseDate,
                    resultSet.getInt("price"),
                    (Student) userDao.getById(resultSet.getInt("student_id")));

            Transaction singleTransaction = new SingleTransaction(resultSet.getInt("bought_artifact_id"),
                    artifact,
                    purchaseDate,
                    payment);

            transactions.add(singleTransaction);
        }
    }

    private List<Payment> createPaymentList(int bought_artifact_id) throws ObjectNotFoundException {
        List<Payment> paymentList = new ArrayList<>();
        String selectStatement = "SELECT * FROM group_buying WHERE bought_artifact_id = ?;";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, bought_artifact_id);
            ResultSet resultSetForGroupBuying = preparedStatement.executeQuery();

            while (resultSetForGroupBuying.next()) {
                LocalDate paymentDate = LocalDate.parse(resultSetForGroupBuying.getString("payment_date"));
                Payment payment = new Payment();
                payment.setStudent((Student) userDao.getById(resultSetForGroupBuying.getInt("student_id")))
                        .setAmount(resultSetForGroupBuying.getInt("student_payment"))
                        .setPaymentDate(paymentDate);

                paymentList.add(payment);
            }
            resultSetForGroupBuying.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found");
        }

        return paymentList;
    }

    public void addTransaction(Transaction transaction) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(insertTransaction);

            //insertTransactionToDatabase(transaction);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Can't insert transaction to database");
        }

    }
}
