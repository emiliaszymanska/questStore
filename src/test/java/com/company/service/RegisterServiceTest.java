package com.company.service;

import com.company.dao.UserDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.user.Mentor;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    @Test
    void testCreateNewUser() throws ObjectNotFoundException, JsonProcessingException {
        // Arrange
        UserDao userDao = mock(UserDao.class);
        User mentor = new Mentor.Builder().build();
        doNothing().when(userDao).insert(mentor);
        when(userDao.getByEmailPassword("email", "password")).thenReturn(mentor);

        User expectedMentor = new Mentor.Builder().build()
                .setTypeId(2)
                .setEmail("email@gmail.com")
                .setPassword("password")
                .setActive(true);

        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.writeValueAsString(expectedMentor)).thenReturn(anyString());

        Map<String, String> map = new HashMap<>();
        map.put("typeId", "2");
        map.put("email", "email@gmail.com");
        map.put("password", "password");
        map.put("isActive", "true");

        RegisterService registerService = new RegisterService(userDao, null, mapper);
        // Act
        registerService.createNewUser(mentor, map);
        // Assert
        assertAll(
                () -> verify(userDao).insert(mentor),
                () -> verify(mapper).writeValueAsString(null),
                () -> assertEquals(expectedMentor, mentor)
        );
    }
}
