package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.Parser;
import com.company.service.RegisterService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {

    @Test
    void isRegistrationProcessSuccessful() throws IOException, ObjectNotFoundException {
        // arrange
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Headers headers = new Headers();
        Mockito.when(exchange.getResponseHeaders()).thenReturn(headers);
        Mockito.doNothing().when(exchange).sendResponseHeaders(Mockito.anyInt(), Mockito.anyLong());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Mockito.when(exchange.getResponseBody()).thenReturn(outputStream);

        Parser parser = Mockito.mock(Parser.class);
        Map<String, String> map = new HashMap<>();
        map.put("typeId", "2");
        Mockito.when(parser.parseFormData(exchange)).thenReturn(map);

        RegisterService registerService = Mockito.mock(RegisterService.class);
        String response = "Response";
        Mockito.when(registerService.createNewUser(Mockito.any(), Mockito.eq(map))).thenReturn(response);

        RegisterController registerController = new RegisterController(parser, registerService);
        // act
        registerController.handle(exchange);
        // assert
        assertAll(
                () -> Mockito.verify(parser, Mockito.times(1)).parseFormData(exchange),
                () -> Mockito.verify(registerService, Mockito.times(1)).createNewUser(Mockito.any(),
                        Mockito.eq(map)),
                () -> assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0)),
                () -> assertEquals("application/json", headers.get("Content-type").get(0)),
                () -> assertEquals("http://localhost:63342", headers.get("Access-Control-Allow-Origin").get(0)),
                () -> assertEquals(response, outputStream.toString())
        );
    }
}