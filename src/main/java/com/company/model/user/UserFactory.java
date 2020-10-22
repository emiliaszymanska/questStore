package com.company.model.user;

import com.company.exceptions.ObjectNotFoundException;

public class UserFactory {

    public User create(int typeId) throws ObjectNotFoundException {

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