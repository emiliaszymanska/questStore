package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;
import com.company.model.user.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao extends UserDao {

    private ModuleTypeDao moduleTypeDao;

    public StudentDao() {
        super();
        this.moduleTypeDao = new ModuleTypeDao();

        selectStatement = "SELECT * FROM users INNER JOIN students ON users.id = students.user_id WHERE id = ?";
        selectAllStatement = "SELECT * FROM users INNER JOIN students ON users.id = students.user_id WHERE user_type_id = 3";
        insertStatement = "INSERT INTO users (first_name, last_name, user_type_id, phone_number, email, password, " +
                "is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        updateStatement = "UPDATE users SET first_name = ?, last_name = ?, user_type_id = ?, phone_number = ?, " +
                "email = ?, password = ?, is_active = ? WHERE id = ?";
        deleteStatement = "DELETE FROM users WHERE id = ?";
    }

    Student parseResultSetWithAdditionalData(ResultSet resultSet) throws SQLException {
        return getStudentWithAdditionalData(resultSet);
    }

    public Student getStudentByEmailWithAdditionalData(String email) throws ObjectNotFoundException {
        String statement = "SELECT * FROM users WHERE email = ?";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Student student = parseResultSetWithAdditionalData(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return student;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found");
        }
    }

    public Student getStudentByIdWithAdditionalData(int id) throws ObjectNotFoundException {
        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Student student = parseResultSetWithAdditionalData(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return student;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found");
        }
    }

    public void insertAdditionalStudentData(Student student) throws ObjectNotFoundException {
        String statement = "INSERT INTO students (user_id, module_id, experience_level, balance) VALUES (?, ?, ?, ?)";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, student.getModuleType().getId());
            preparedStatement.setInt(3, student.getExperienceLevel());
            preparedStatement.setInt(4, student.getBalance());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in students");
        }
    }

    public void updateAdditionalStudentData(Student student) throws ObjectNotFoundException {
        String statement = "UPDATE students SET module_id = ?, experience_level = ?, balance = ? WHERE user_id = ?";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setInt(1, student.getModuleType().getId());
            preparedStatement.setInt(2, student.getExperienceLevel());
            preparedStatement.setInt(3, student.getBalance());
            preparedStatement.setInt(4, student.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in students");
        }
    }

    public void deleteAdditionalStudentData(Student student) throws ObjectNotFoundException {
        String statement = "DELETE FROM students WHERE user_id = ?";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in students");
        }
    }

    private Student getStudentWithAdditionalData(ResultSet resultSet) throws SQLException {
        return new Student.Builder()
                .withId(resultSet.getInt("id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withTypeId(resultSet.getInt("user_type_id"))
                .withPhoneNumber(resultSet.getString("phone_number"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .withIsActive(resultSet.getBoolean("is_active"))
                .withModuleType(moduleTypeDao.getTypeById(resultSet.getInt("module_id")))
                .withExperienceLevel(resultSet.getInt("experience_level"))
                .withBalance(resultSet.getInt("balance"))
                .build();
    }
}
