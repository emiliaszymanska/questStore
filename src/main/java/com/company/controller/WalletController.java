package com.company.controller;

import com.company.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class WalletController implements HttpHandler {
//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        String method = exchange.getRequestMethod();
//        String response = "";
//
//        try {
//            switch (method){
//                case "GET":
//                    response = get(exchange);
////                    System.out.println(response);
//                    sendResponse(response, exchange, 200);
//                    break;
//                case "POST":
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = e.getMessage();
//            sendResponse(response, exchange, 404);
//        }
//    }
//
//    private String get(HttpExchange exchange) throws ObjectNotFoundException, JsonProcessingException {
//        String url = exchange.getRequestURI().getRawPath();
//        String[] actions = url.split("/");
////        System.out.println("URL: " + url + " | actions: " + Arrays.toString(actions));
//        ObjectMapper mapper = new ObjectMapper();
//
//        return mapper.writeValueAsString(questDao.getAll());
//    }

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
        }
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("http://localhost:63342"));
        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
