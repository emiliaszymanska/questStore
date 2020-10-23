package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.Actions;
import com.company.helpers.Parser;
import com.company.service.ArtifactService;
import com.company.service.RegisterService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtifactControllerTest {

    @Test
    public void testIsAddingProcessSuccessful() throws IOException, ObjectNotFoundException {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Headers headers = new Headers();

        Mockito.when(exchange.getResponseHeaders()).thenReturn(headers);
        Mockito.doNothing().when(exchange).sendResponseHeaders(Mockito.anyInt(), Mockito.anyLong());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(exchange.getResponseBody()).thenReturn(byteArrayOutputStream);

        Parser parser = Mockito.mock(Parser.class);
        Map<String, String> map = new HashMap<>();
        map.put("typeId", "2");
        Mockito.when(parser.parseFormData(exchange)).thenReturn(map);

        ArtifactService artifactService = Mockito.mock(ArtifactService.class);
        String response = "Response";
        Mockito.when(artifactService.addArtifact(Mockito.eq(map))).thenReturn(response);

        ArtifactController artifactController = new ArtifactController(parser, artifactService);

        Actions actions = Mockito.mock(Actions.class);
        artifactController.get(exchange, actions);
        artifactController.post(exchange, actions);
        artifactController.handle(exchange);

        assertAll(
//                () -> Mockito.verify(artifactService, Mockito.times(1)).getAll(),
//                () -> Mockito.verify(artifactService, Mockito.times(1)).getById(Mockito.any()),
//                () -> Mockito.verify(artifactService, Mockito.times(1)).addArtifact(Mockito.eq(map)),
//                () -> Mockito.verify(artifactService, Mockito.times(1)).updateArtifact(Mockito.eq(map)),
//                () -> Mockito.verify(artifactService, Mockito.times(1)).deleteArtifact(Mockito.eq(map)),
                () -> Mockito.verify(parser, Mockito.times(1)).parseFormData(exchange),
                () -> assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0)),
                () -> assertEquals("application/json", headers.get("Content-type").get(0)),
                () -> assertEquals("http://localhost:63342", headers.get("Access-Control-Allow-Origin").get(0)),
                () -> assertEquals(response, byteArrayOutputStream.toString())
        );
    }
}