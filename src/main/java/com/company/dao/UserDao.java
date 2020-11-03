package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;
import com.company.model.user.User;
import com.company.model.user.UserFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDao extends Dao<User> {

    private String loginStatement = "SELECT * FROM users WHERE email = ? and password = ?";
    private String selectSessionIdStatement = "SELECT * FROM users WHERE session_id = ?";
    private String updateSessionIdStatement = "UPDATE users SET session_id = ? WHERE email = ? AND password = ?";

    public UserDao() {
        super("users");

        selectStatement = "SELECT * FROM users WHERE id = ?";
        selectAllStatement = "SELECT * FROM users WHERE user_type_id = 2";
        insertStatement = "INSERT INTO users (first_name, last_name, user_type_id, phone_number, email, password, " +
                "is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        updateStatement = "UPDATE users SET first_name = ?, last_name = ?, user_type_id = ?, phone_number = ?, " +
                "email = ?, password = ?, is_active = ? WHERE id = ?";
        deleteStatement = "DELETE FROM users WHERE id = ?";
    }

    @Override
    User parseResultSet(ResultSet resultSet) throws SQLException {
        return getUser(resultSet);
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        UserFactory userFactory = new UserFactory();
        int typeId = resultSet.getInt("user_type_id");
        User user = userFactory.create(typeId);

        return createUserData(user, resultSet);
    }

    private User createUserData(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setTypeId(resultSet.getInt("user_type_id"))
                .setPhoneNumber(resultSet.getString("phone_number"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setActive(resultSet.getBoolean("is_active"));

        return user;
    }

    @Override
    List<User> getAllObjects(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(getUser(resultSet));
        }
        return users;
    }

    @Override
    void specifyPreparedStatement(User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setInt(3, user.getTypeId());
        preparedStatement.setString(4, user.getPhoneNumber());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setBoolean(7, user.isActive());
    }

    @Override
    void addIdToUpdateStatement(User user) throws SQLException {
        preparedStatement.setInt(8, user.getId());
    }

    @Override
    void specifyDeleteStatement(User user) throws SQLException {
        preparedStatement.setInt(1, user.getId());
    }

    public User getByEmailPassword(String email, String password) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(loginStatement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User user = getUser(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in users");
        }
    }

    public User getBySessionId(UUID uuid) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(selectSessionIdStatement);
            preparedStatement.setObject(1, uuid);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User user = getUser(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in users");
        }
    }

    public void updateSessionId(UUID uuid, String email, String password) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(updateSessionIdStatement);
            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in users");
        }
    }
}
