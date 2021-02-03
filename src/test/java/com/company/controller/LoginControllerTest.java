package com.company.controller;

import com.company.helpers.Parser;
import com.company.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void loginControllerHandle_test() throws IOException {
        //Arrange
        HttpExchange exchange = mock(HttpExchange.class);

        Map<String, String> data = new HashMap<>();
        data.put("email", "email@gmail.com");
        data.put("password", "asd");

        Parser parser = mock(Parser.class);
        when(parser.parseFormData(exchange)).thenReturn(data);

        SessionController sessionController = new SessionController();

        ObjectMapper objectMapper = mock(ObjectMapper.class);
        LoginService loginService = mock(LoginService.class);

        Headers headers = new Headers();

        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(objectMapper.writeValueAsString(null)).thenReturn("response");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(byteArrayOutputStream);

        LoginController loginController = new LoginController(objectMapper, parser, sessionController, loginService);
        //Act
        loginController.handle(exchange);
        //Assert
        assertAll(
                () -> verify(objectMapper).writeValueAsString(null),
                () -> verify(parser).parseFormData(exchange),
                () -> assertTrue(sessionController.sessions.containsValue(null)),
                () -> verify(loginService).getUserByEmailAndPassword(data.get("email"), data.get("password")),
                () -> assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0)),
                () -> assertEquals("application/json", headers.get("Content-type").get(0)),
                () -> assertEquals("http://localhost:63342", headers.get("Access-Control-Allow-Origin").get(0)),
                () -> assertEquals("response", byteArrayOutputStream.toString())
        );
    }

}