package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.Parser;
import com.company.service.RegisterService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    @Test
    void testRegistrationProcessSuccessful() throws IOException, ObjectNotFoundException {
        // Arrange
        HttpExchange exchange = mock(HttpExchange.class);
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        doNothing().when(exchange).sendResponseHeaders(anyInt(), anyLong());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        Parser parser = mock(Parser.class);
        Map<String, String> map = new HashMap<>();
        map.put("typeId", "2");
        when(parser.parseFormData(exchange)).thenReturn(map);

        RegisterService registerService = mock(RegisterService.class);
        String response = "Response";
        when(registerService.createNewUser(any(), eq(map))).thenReturn(response);

        RegisterController registerController = new RegisterController(parser, registerService);
        // Act
        registerController.handle(exchange);
        // Assert
        assertAll(
                () -> verify(parser).parseFormData(exchange),
                () -> verify(registerService).createNewUser(any(), eq(map)),
                () -> assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0)),
                () -> assertEquals("application/json", headers.get("Content-type").get(0)),
                () -> assertEquals("http://localhost:63342", headers.get("Access-Control-Allow-Origin").get(0)),
                () -> assertEquals(response, outputStream.toString())
        );
    }
}
