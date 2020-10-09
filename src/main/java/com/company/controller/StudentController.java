package com.company.controller;

import com.company.dao.StudentDao;
import com.company.dao.TransactionDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Artifact;
import com.company.model.Quest;
import com.company.model.Transaction;
import com.company.model.Wallet;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class StudentController implements HttpHandler {

    private SessionController sessionController;
    private StudentDao studentDao;
    private Artifact artifact;
    private Wallet wallet;
    private Quest quest;
    private String[] actions;
    private String response = "";
    private ObjectMapper mapper;


    public StudentController(SessionController sessionController) {
        this.sessionController = sessionController;
        studentDao = new StudentDao();
        artifact = new Artifact();
        wallet = new Wallet();
        quest = new Quest();
        mapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String url = exchange.getRequestURI().getRawPath();
        actions = url.split("/");

        try {
            switch (method) {
                case "GET":
                    get(exchange);
                    break;
                case "POST":
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get(HttpExchange exchange) throws IOException, ObjectNotFoundException {
        UUID uuid = actions.length == 4 ? UUID.fromString(actions[3]) : null;

        switch (actions[2]) {
            case "wallet":
                response = getStudentWallet(uuid);
                break;
            case "quests":
                response = getStudentQuests(uuid);

                break;
            case "store":
                response = getStudentStore(uuid);
                break;
            default:

                break;
        }

        sendResponse(response, exchange, 200);
    }

    private void post(HttpExchange exchange) {

    }


    private String getStudentStore(UUID uuid) throws ObjectNotFoundException {
        User student = studentDao.getBySessionId(uuid);

        return "store";
    }

    private String getStudentQuests(UUID uuid) throws ObjectNotFoundException {
        User student = studentDao.getBySessionId(uuid);

        return "quest";
    }

    private String getStudentWallet(UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        TransactionDao transactionDao = new TransactionDao();

        User student = studentDao.getBySessionId(uuid);
        List<Transaction> transactions = transactionDao.getTransactionsByStudentId(student.getId());
        return mapper.writeValueAsString(transactions);
    }

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        }

        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
