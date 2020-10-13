package com.company.controller;

import com.company.dao.StudentDao;
import com.company.dao.TransactionDao;
import com.company.dao.UserDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.Parser;
import com.company.helpers.HttpHelper;
import com.company.model.Artifact;
import com.company.model.Quest;
import com.company.model.Transaction;
import com.company.model.Wallet;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.util.Collections;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
        this.studentDao = new StudentDao();
        this.artifact = new Artifact();
        this.wallet = new Wallet();
        this.quest = new Quest();
        this.mapper = new ObjectMapper();
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
                    post(exchange);
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
            case "profile":
                response = getStudentProfile(uuid);
                break;
            case "data":
                getStudentBalanceAndExperience(uuid);
            default:
                break;
        }
        HttpHelper.sendResponse(exchange, response, 200);
    }

    private void getStudentBalanceAndExperience(UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        User student = studentDao.getBySessionId(uuid);
        student = studentDao.getStudentByIdWithAdditionalData(student.getId());
        response = mapper.writeValueAsString(student);
    }

    private void post(HttpExchange exchange) throws IOException, ObjectNotFoundException {
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Map<String, String> data = Parser.parseFormData(bufferedReader.readLine());
        String firstName = data.get("firstName");
        String lastName = data.get("lastName");
        String email = data.get("email");
        String phoneNumber = data.get("phoneNumber");
        System.out.println(email);
        System.out.println(phoneNumber);

        switch (actions[2]) {
            case "update":
                UUID uuid = actions.length == 4 ? UUID.fromString(actions[3]) : null;
                User student = studentDao.getBySessionId(uuid);
                student.setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setPhoneNumber(phoneNumber);
                System.out.println(student.toString());
                studentDao.update(student);
                HttpHelper.sendResponse(exchange, mapper.writeValueAsString(student), 200);
                break;
            default:
                break;
        }
    }

    private String getStudentProfile(UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        User student = studentDao.getBySessionId(uuid);

        return mapper.writeValueAsString(student);
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
}
