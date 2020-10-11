package com.company.controller;

import com.company.dao.ArtifactDao;
import com.company.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class ArtifactController implements HttpHandler {

    private ArtifactDao artifactDao;

    public ArtifactController() {
        this.artifactDao = new ArtifactDao();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            switch (method){
                case "GET":
                    response = get(exchange);
                    ResponseController.sendResponse(exchange, response, 200);
                    break;
                case "POST":
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            ResponseController.sendResponse(exchange, response, 404);
        }
    }

    private String get(HttpExchange exchange) throws ObjectNotFoundException, JsonProcessingException {
        String url = exchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        ObjectMapper mapper = new ObjectMapper();

        if (actions.length == 3) {
            int id = Integer.parseInt(actions[2]);
            System.out.println(id);
            return mapper.writeValueAsString(artifactDao.getById(id));
        }
        return mapper.writeValueAsString(artifactDao.getAll());
    }
}
