package com.company.model.user;

import com.company.exceptions.ObjectNotFoundException;

import java.sql.ResultSet;

public class UserFactory {

    private ResultSet resultSet;

    public UserFactory(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public UserFactory() {
    }

    public User create(int typeId) throws ObjectNotFoundException {

        switch (typeId) {
            case 1:
                //TODO Admin model
                //Admin
                //return new Admin.Builder().build();
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
