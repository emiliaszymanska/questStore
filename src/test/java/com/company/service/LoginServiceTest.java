package com.company.service;

import com.company.dao.StudentDao;
import com.company.dao.UserDao;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    UserDao userDao = mock(UserDao.class);
    StudentDao studentDao = mock(StudentDao.class);

    @Test
    void getUserByEmailAndPassword_ReturnStudentWithAllData_WhenEmailAndPasswordGiven() {
        //Arrange

        //Act

        //Assert

    }

}