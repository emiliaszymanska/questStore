package com.company.controller;

import com.company.helpers.Parser;
import com.company.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void loginControllerHandle_test() throws IOException {
        //Arrange
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        InputStreamReader isr = Mockito.mock(InputStreamReader.class);
        //Mockito.when(exchange.getRequestBody()).thenReturn(isr);

        BufferedReader br = Mockito.mock(BufferedReader.class);

        Map<String, String> data = new HashMap<>();
        data.put("email", "email@gmail.com");
        data.put("password", "asd");

        SessionController sessionController = Mockito.mock(SessionController.class);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        Parser parser = Mockito.mock(Parser.class);
        LoginService loginService = Mockito.mock(LoginService.class);

        LoginController loginController = new LoginController(objectMapper, parser, sessionController, loginService);
        //Act
        loginController.handle(exchange);
        //Assert
        assertAll(

        );
    }

}