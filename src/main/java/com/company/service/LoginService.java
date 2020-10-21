package com.company.service;

import com.company.dao.StudentDao;
import com.company.dao.UserDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.user.Student;
import com.company.model.user.User;

import java.util.UUID;

public class LoginService {
    private UserDao userDao;

    public LoginService() {
        this.userDao = new UserDao();
    }

    public User getUserByEmailAndPassword(String email, String password) throws ObjectNotFoundException {
        User user = userDao.getByEmailPassword(email, password);

        if (user instanceof Student) {
            StudentDao studentDao = new StudentDao();
            user = studentDao.getStudentByIdWithAdditionalData(user.getId());
        }

        return user;
    }

    public void updateSessionIdByEmailAndPassword(UUID uuid, String email, String password) throws ObjectNotFoundException {
        userDao.updateSessionId(uuid, email, password);
    }
}
