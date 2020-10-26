package com.company.controller;

import com.company.helpers.Parser;
import com.company.model.user.User;
import com.company.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void loginControllerHandle_test() throws IOException {
        //Arrange
        HttpExchange exchange = Mockito.mock(HttpExchange.class);

        Map<String, String> data = new HashMap<>();
        data.put("email", "email@gmail.com");
        data.put("password", "asd");

        Parser parser = Mockito.mock(Parser.class);
        Mockito.when(parser.parseFormData(exchange)).thenReturn(data);

        SessionController sessionController = new SessionController();

        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        LoginService loginService = Mockito.mock(LoginService.class);

        Headers headers = new Headers();

        Mockito.when(exchange.getResponseHeaders()).thenReturn(headers);
        Mockito.when(objectMapper.writeValueAsString(null)).thenReturn("response");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(exchange.getResponseBody()).thenReturn(byteArrayOutputStream);

        LoginController loginController = new LoginController(objectMapper, parser, sessionController, loginService);
        //Act
        loginController.handle(exchange);
        //Assert
        assertAll(
                () -> Mockito.verify(objectMapper).writeValueAsString(null),
                () -> Mockito.verify(parser).parseFormData(exchange),
                () -> assertTrue(sessionController.sessions.containsValue(null)),
                () -> Mockito.verify(loginService).getUserByEmailAndPassword(data.get("email"), data.get("password")),
                () -> assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0)),
                () -> assertEquals("application/json", headers.get("Content-type").get(0)),
                () -> assertEquals("http://localhost:63342", headers.get("Access-Control-Allow-Origin").get(0)),
                () -> assertEquals("response", byteArrayOutputStream.toString())
        );
    }

}