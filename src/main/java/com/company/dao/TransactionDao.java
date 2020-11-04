package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;
import com.company.model.*;
import com.company.model.user.Student;

import java.sql.*;
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
    private final String byBoughtArtifactId = "WHERE ba.id = ?;";
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

    public List<Transaction> getTransactionByBoughtArtifactId(int boughtArtifactId) throws ObjectNotFoundException {
        return getTransactionsById(selectTransactions + byBoughtArtifactId, boughtArtifactId);
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

    public void insertTransaction(Transaction transaction) throws ObjectNotFoundException {
        int boughtArtifactId = insertIntoBoughtArtifacts(transaction);
        if (transaction instanceof GroupTransaction) {
            insertIntoGroupBuying((GroupTransaction) transaction, boughtArtifactId);
        }
    }

    private int insertIntoBoughtArtifacts(Transaction transaction) throws ObjectNotFoundException {
        String insertStatement = "INSERT INTO bought_artifacts (purchase_date, artifact_id, student_id) VALUES (?, ?, ?)";
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, transaction.getPurchaseDate().toString());
            preparedStatement.setInt(2, transaction.getArtifact().getId());
            if (transaction instanceof SingleTransaction) {
                preparedStatement.setInt(3, ((SingleTransaction) transaction).getPayment().getStudent().getId());
            } else if (transaction instanceof GroupTransaction) {
                preparedStatement.setInt(3, ((GroupTransaction) transaction).getPayments().get(0).getStudent().getId());
            }

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int boughtArtifactId = 0;

            while (generatedKeys.next()) {
                boughtArtifactId = generatedKeys.getInt("id");
            }

            preparedStatement.close();
            CONNECTOR.connection.close();

            return boughtArtifactId;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Can't insert transaction to database");
        }
    }

    public void insertIntoGroupBuying(GroupTransaction transaction, int boughtArtifactId) throws ObjectNotFoundException {
        String insertStatement = "INSERT INTO group_buying (student_payment, payment_date, bought_artifact_id, student_id) VALUES (?, ?, ?, ?)";
        try {
            List<Payment> paymentList = transaction.getPayments();
            for (int index = 0; index < paymentList.size(); index++) {
                Payment singlePayment = paymentList.get(index);

                CONNECTOR.connect();
                preparedStatement = CONNECTOR.connection.prepareStatement(insertStatement);
                preparedStatement.setInt(1, singlePayment.getAmount());
                preparedStatement.setDate(2, Date.valueOf(singlePayment.getPaymentDate()));
                preparedStatement.setInt(3, boughtArtifactId);
                preparedStatement.setInt(4, singlePayment.getStudent().getId());


                preparedStatement.executeUpdate();

                preparedStatement.close();
                CONNECTOR.connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Can't insert group transaction to database");
        }
    }

    public void updatePurchaseDate(LocalDate date, int boughtArtifactId) throws ObjectNotFoundException {
        String updateStatement = "UPDATE bought_artifacts SET purchase_date = ? WHERE id = ?";
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(updateStatement);
            preparedStatement.setString(1, date.toString());
            preparedStatement.setInt(2, boughtArtifactId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Update can't be done to this object");
        }
    }
}
