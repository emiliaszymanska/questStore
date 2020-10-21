package com.company.model.user;

import com.company.dao.StudentDao;
import com.company.exceptions.ObjectNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {

    private ResultSet resultSet;

    public UserFactory(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public UserFactory() {
    }

    public User create(int typeId) throws SQLException {

        switch (typeId) {
            case 1:
                //Admin
                //return new Admin.Builder().build();
                //TODO Admin model
                break;
            case 2:
                //Mentor
                return new Mentor.Builder().build();
            case 3:
                //Student
                return new Student.Builder().build();
        }
        throw new ObjectNotFoundException("User not found");
    }
}