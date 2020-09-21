package com.company.dao;

import com.company.model.user.Mentor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDao extends Dao<Mentor> {

    private List<Mentor> allMentors;

    public MentorDao() {
        super("users");
        this.allMentors = new ArrayList<>();

        selectStatement = "SELECT * FROM users WHERE id = ?";
        selectAllStatement = "SELECT * FROM users WHERE user_type_id = 2";
        insertStatement = "INSERT INTO users (first_name, last_name, user_type_id, phone_number, email, password, " +
                "is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        updateStatement = "UPDATE users SET first_name = ?, last_name = ?, user_type_id = ?, phone_number = ?, " +
                "email = ?, password = ?, is_active = ? WHERE id = ?";
        deleteStatement = "DELETE FROM users WHERE id = ?";
    }

    @Override
    Mentor parseResultSet(ResultSet resultSet) throws SQLException {
        return getMentor(resultSet);
    }

    @Override
    List<Mentor> getAllObjects(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            allMentors.add(getMentor(resultSet));
        }
        return allMentors;
    }

    @Override
    void specifyPreparedStatement(Mentor mentor) throws SQLException {
        preparedStatement.setString(1, mentor.getFirstName());
        preparedStatement.setString(2, mentor.getLastName());
        preparedStatement.setInt(3, mentor.getTypeId());
        preparedStatement.setString(4, mentor.getPhoneNumber());
        preparedStatement.setString(5, mentor.getEmail());
        preparedStatement.setString(6, mentor.getPassword());
        preparedStatement.setBoolean(7, mentor.isActive());
    }

    @Override
    void addIdToUpdateStatement(Mentor mentor) throws SQLException {
        preparedStatement.setInt(8, mentor.getId());
    }

    @Override
    void specifyDeleteStatement(Mentor mentor) throws SQLException {
        preparedStatement.setInt(1, mentor.getId());
    }

    private Mentor getMentor(ResultSet resultSet) throws SQLException {
        return new Mentor.Builder()
                .withId(resultSet.getInt("id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withTypeId(resultSet.getInt("user_type_id"))
                .withPhoneNumber(resultSet.getString("phone_number"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .withIsActive(resultSet.getBoolean("is_active"))
                .build();
    }
}
