package com.company.controller;

import com.company.dao.QuestDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.ActionParser;
import com.company.helpers.Actions;
import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.service.ArtifactService;
import com.company.service.QuestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class QuestController implements HttpHandler {

    private QuestService questService;
    private final ActionParser actionParser;
    private final Parser parser;

    public QuestController() {
        this.questService = new QuestService();
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
        }
    }

    public void get(HttpExchange exchange, Actions actions) throws ObjectNotFoundException, IOException {
        String response;

        switch (actions.getActionsSize()) {
            case 2:
                response = questService.getAll();
                break;
            case 3:
                response = questService.getById(actions.getThirdComponent());
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
                response = questService.addQuest(formData);
                break;
            case "update":
                response = questService.updateQuest(formData);
                break;
            case "delete":
                response = questService.deleteQuest(formData);
                break;
            default:
                HttpHelper.sendResponse(exchange, "Invalid URL", 404);
                return;
        }
        HttpHelper.sendResponse(exchange, response, 200);
    }
}
