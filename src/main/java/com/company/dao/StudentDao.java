package com.company.dao;

import com.company.exceptions.ObjectNotFoundException;
import com.company.model.user.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends Dao<Student> {

    private ModuleTypeDao moduleTypeDao;
    private List<Student> allStudents;

    public StudentDao() {
        super("users");
        this.moduleTypeDao = new ModuleTypeDao();
        this.allStudents = new ArrayList<>();

        selectStatement = "SELECT * FROM users INNER JOIN students ON users.id = students.user_id WHERE id = ?";
        selectAllStatement = "SELECT * FROM users INNER JOIN students ON users.id = students.user_id WHERE user_type_id = 3";
        insertStatement = "INSERT INTO users (first_name, last_name, user_type_id, phone_number, email, password, " +
                "is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        updateStatement = "UPDATE users SET first_name = ?, last_name = ?, user_type_id = ?, phone_number = ?, " +
                "email = ?, password = ?, is_active = ? WHERE id = ?";
        deleteStatement = "DELETE FROM users WHERE id = ?";
    }

    @Override
    Student parseResultSet(ResultSet resultSet) throws SQLException {
        return getStudent(resultSet);
    }

    Student parseResultSetWithoutAdditionalData(ResultSet resultSet) throws SQLException {
        return getStudentWithoutAdditionalData(resultSet);
    }

    @Override
    List<Student> getAllObjects(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            allStudents.add(getStudent(resultSet));
        }
        return allStudents;
    }

    @Override
    public void specifyPreparedStatement(Student student) throws SQLException {
        preparedStatement.setString(1, student.getFirstName());
        preparedStatement.setString(2, student.getLastName());
        preparedStatement.setInt(3, student.getTypeId());
        preparedStatement.setString(4, student.getPhoneNumber());
        preparedStatement.setString(5, student.getEmail());
        preparedStatement.setString(6, student.getPassword());
        preparedStatement.setBoolean(7, student.isActive());
    }

    @Override
    void addIdToUpdateStatement(Student student) throws SQLException {
        preparedStatement.setInt(8, student.getId());
    }

    @Override
    void specifyDeleteStatement(Student student) throws SQLException {
        preparedStatement.setInt(1, student.getId());
    }

    public Student getStudentByEmailWithoutAdditionalData(String email) throws ObjectNotFoundException {
        String statement = "SELECT * FROM users WHERE email = ?";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Student student = parseResultSetWithoutAdditionalData(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return student;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in users");
        }
    }

    public Student getStudentByIdWithoutAdditionalData(int id) throws ObjectNotFoundException {
        String statement = "SELECT * FROM users WHERE id = ?";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Student student = parseResultSetWithoutAdditionalData(resultSet);

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

            return student;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in users");
        }
    }

    public void insertAdditionalStudentData(Student student) throws ObjectNotFoundException {
        String statement = "INSERT INTO students (user_id, module_id, experience_level) VALUES (?, ?, ?)";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, student.getModuleType().getId());
            preparedStatement.setInt(3, student.getExperienceLevel());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException("Object not found in students");
        }
    }

    public void updateAdditionalStudentData(Student student) throws ObjectNotFoundException {
        String statement = "UPDATE students SET module_id = ?, experience_level = ? WHERE user_id = ?";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(statement);
            preparedStatement.setInt(1, student.getModuleType().getId());
            preparedStatement.setInt(2, student.getExperienceLevel());
            preparedStatement.setInt(3, student.getId());
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

    private Student getStudent(ResultSet resultSet) throws SQLException {
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
                .build();
    }

    private Student getStudentWithoutAdditionalData(ResultSet resultSet) throws SQLException {
        return new Student.Builder()
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
