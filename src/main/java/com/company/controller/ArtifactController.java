package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.service.ArtifactService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ArtifactController implements HttpHandler {

    private ArtifactService artifactService;
    private final ActionParser actionParser;
    private final Parser parser;

    public ArtifactController() {
        artifactService = new ArtifactService();
        this.actionParser = new ActionParser();
        this.parser = new Parser();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String url = exchange.getRequestURI().getRawPath();
        Actions actions = actionParser.fromURL(url);

        try {
            switch (method) {
                case "GET":
                    get(exchange, actions);
                    break;
                case "POST":
                    post(exchange, actions);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            HttpHelper.sendResponse(exchange, response, 404);
        }
    }

    private void get(HttpExchange exchange, Actions actions) throws ObjectNotFoundException, JsonProcessingException {
        String url = exchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        HttpHelper.sendResponse(exchange, response, 200);

    }

    private void post(HttpExchange exchange, Actions actions) {

    }
}

/*
mapper do serwisu
 */