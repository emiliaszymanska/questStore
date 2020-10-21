package com.company.service;

import com.company.model.ModuleType;
import com.company.model.user.Student;
import com.company.model.user.User;

public class RegisterService {

    public User createUserData(User user, String firstName, String lastName, int typeId, String phoneNumber,
                               String email, String password, boolean isActive) {
        user.setFirstName(firstName)
                .setLastName(lastName)
                .setTypeId(typeId)
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .setPassword(password)
                .setActive(isActive);

        return user;
    }

    public User createAdditionalStudentData(User user, ModuleType moduleType, int experienceLevel, int balance) {
        ((Student) user).setModuleType(moduleType)
                .setExperienceLevel(experienceLevel)
                .setBalance(balance);

        return user;
    }
}
