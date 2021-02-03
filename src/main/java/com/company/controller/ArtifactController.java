package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.ActionParser;
import com.company.helpers.Actions;
import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.service.ArtifactService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class ArtifactController implements HttpHandler {

    private ArtifactService artifactService;
    private final ActionParser actionParser;
    private final Parser parser;

    public ArtifactController() {
        this.artifactService = new ArtifactService();
        this.actionParser = new ActionParser();
        this.parser = new Parser();
    }

    @Override
    public void handle(HttpExchange exchange) {
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
        }
    }

    public void get(HttpExchange exchange, Actions actions) throws ObjectNotFoundException, IOException {
        String response;

        switch (actions.getActionsSize()) {
            case 2:
                response = artifactService.getAll();
                break;
            case 3:
                response = artifactService.getById(actions.getThirdComponent());
                break;
            default:
                response = "Invalid URL";
                break;
        }
        HttpHelper.sendResponse(exchange, response, 200);
    }

    public void post(HttpExchange exchange, Actions actions) throws IOException, ObjectNotFoundException {
        Map<String, String> formData = parser.parseFormData(exchange);
        String response;

        switch (actions.getOperation()) {
            case "add":
                response = artifactService.addArtifact(formData);
                break;
            case "update":
                response = artifactService.updateArtifact(formData);
                break;
            case "delete":
                response = artifactService.deleteArtifact(formData);
                break;
            default:
                HttpHelper.sendResponse(exchange, "Invalid URL", 404);
                return;
        }
        HttpHelper.sendResponse(exchange, response, 200);
    }
}
