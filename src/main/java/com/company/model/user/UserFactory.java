package com.company.model.user;

import com.company.dao.StudentDao;
import com.company.exceptions.ObjectNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {

    private ResultSet resultSet;

    public UserFactory (ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public User create() throws SQLException {
        int type = resultSet.getInt("user_type_id");

        switch (type) {
            case 1:
                //Admin
                //User admin = new Admin.Builder().build();
                //return createUserData(admin);
                //TODO Admin model
                break;
            case 2:
                //Mentor
                User mentor = new Mentor.Builder().build();
                return createUserData(mentor);
            case 3:
                //Student
                User student = new Student.Builder().build();
                return createUserData(student);
        }
        throw new ObjectNotFoundException("User not found");
    }

    private User createUserData(User user) throws SQLException {
        user.setId(resultSet.getInt("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setTypeId(resultSet.getInt("user_type_id"))
                .setPhoneNumber(resultSet.getString("phone_number"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setActive(resultSet.getBoolean("is_active"));

        if (user instanceof Student) {
            StudentDao studentDao = new StudentDao();
            studentDao.getStudentByIdWithAdditionalData(user.getId());
        }

        return user;
    }

}
