package com.company.controller;

import com.company.dao.MentorDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class MentorController implements HttpHandler {

    private SessionController sessionController;
    private MentorDao mentorDao;
    private String[] actions;
    private String response = "";
    private ObjectMapper mapper;

    public MentorController(SessionController sessionController) {
        this.sessionController = sessionController;
        mentorDao = new MentorDao();
        mapper = new ObjectMapper();
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
