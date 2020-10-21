package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.ActionParser;
import com.company.helpers.Actions;
import com.company.helpers.Parser;
import com.company.helpers.HttpHelper;
import com.company.service.StudentService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class StudentController implements HttpHandler {

    private StudentService studentService;
    private final ActionParser actionParser;
    private final Parser parser;

    public StudentController() {
        this.studentService = new StudentService();
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

    private void get(HttpExchange exchange, Actions actions) throws IOException, ObjectNotFoundException {
        Optional<UUID> optionalUUID = actions.getUUID();

        if (optionalUUID.isEmpty()) {
            HttpHelper.sendResponse(exchange, "Not logged in", 403);
        }

        UUID uuid = optionalUUID.get();
        String response;

        switch (actions.getEntity()) {
            case "wallet":
                response = studentService.getStudentWallet(uuid);
                break;
            case "quests":
                response = studentService.getStudentQuests(uuid);
                break;
            case "store":
                response = studentService.getStudentStore(uuid);
                break;
            case "profile":
                response = studentService.getStudentProfile(uuid);
                break;
            case "data":
                response = studentService.getStudentBalanceAndExperience(uuid);
                break;
            default:
                HttpHelper.sendResponse(exchange, "Invalid URL", 404);
                return;
        }
        HttpHelper.sendResponse(exchange, response, 200);
    }

    private void post(HttpExchange exchange, Actions actions) throws IOException, ObjectNotFoundException {
        Map<String, String> formData = parser.parseFormData(exchange);
        String response;

        switch (actions.getOperation()) {
            case "update": // /student/update
                response = studentService.updateStudent(formData, actions.getUUID().get());
                break;
            default:
                HttpHelper.sendResponse(exchange, "Invalid URL", 404);
                return;
        }
        HttpHelper.sendResponse(exchange, response, 200);
    }
}
