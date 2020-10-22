package com.company.service;

import com.company.dao.StudentDao;
import com.company.dao.UserDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.ModuleType;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class RegisterService {

    public String createNewUser(User user, Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = populateUserObjectWithData(user, formData);

        if (user instanceof Student) {
            newUser = populateStudentObjectWithAdditionalData(formData);
        }
        System.out.println("New user: " + newUser.toString());

        return objectMapper.writeValueAsString(newUser);
    }

    private User populateUserObjectWithData(User user, Map<String, String> formData) throws ObjectNotFoundException {
        UserDao userDao = new UserDao();

        user.setFirstName(formData.get("firstName"))
                .setLastName(formData.get("lastName"))
                .setTypeId(Integer.parseInt(formData.get("typeId")))
                .setPhoneNumber(formData.get("phoneNumber"))
                .setEmail(formData.get("email"))
                .setPassword(formData.get("password"))
                .setActive(true);

        userDao.insert(user);
        User newUser = userDao.getByEmailPassword(formData.get("email"), formData.get("password"));

        return newUser;
    }

    private User populateStudentObjectWithAdditionalData(Map<String, String> formData) throws ObjectNotFoundException {
        StudentDao studentDao = new StudentDao();
        Student student = (Student) studentDao.getByEmailPassword(formData.get("email"), formData.get("password"));

        student.setModuleType(ModuleType.valueOf("PROGRAMMING_BASICS"))
                .setExperienceLevel(1)
                .setBalance(0);

        studentDao.insertAdditionalStudentData(student);

        return student;
    }
}
