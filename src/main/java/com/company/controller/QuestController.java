package com.company.controller;

import com.company.dao.QuestDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.helpers.ActionParser;
import com.company.helpers.Actions;
import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class QuestController implements HttpHandler {

    private QuestDao questDao;
    private final ActionParser actionParser;
    private final Parser parser;

    public QuestController() {
        this.questDao = new QuestDao();
        this.actionParser = new ActionParser();
        this.parser = new Parser();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
//
//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        String method = exchange.getRequestMethod();
//        String url = exchange.getRequestURI().getRawPath();
//        Actions actions = actionParser.fromURL(url);
//
//        try {
//            switch (method){
//                case "GET":
//                    get(exchange, actions);
//                    break;
//                case "POST":
//                    post(exchange, actions);
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String get(HttpExchange exchange, Actions actions) throws ObjectNotFoundException, JsonProcessingException {
//        String url = exchange.getRequestURI().getRawPath();
//        String[] actions = url.split("/");
//        ObjectMapper mapper = new ObjectMapper();
//
//        if (actions.length == 3) {
//            int id = Integer.parseInt(actions[2]);
//            System.out.println(id);
//            return mapper.writeValueAsString(questDao.getById(id));
//        }
//        return mapper.writeValueAsString(questDao.getAll());
//    }
}
