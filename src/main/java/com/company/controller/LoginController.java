package com.company.controller;

import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.model.user.User;
import com.company.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;

public class LoginController implements HttpHandler {

    private ObjectMapper mapper;
    private Parser parser;
    private SessionController sessionController;
    private LoginService loginService;

    public LoginController(ObjectMapper mapper, Parser parser, SessionController sessionController, LoginService loginService) {
        this.mapper = mapper;
        this.parser = parser;
        this.sessionController = sessionController;
        this.loginService = loginService;
    }

    public LoginController(SessionController sessionController) {
        this.mapper = new ObjectMapper();
        this.parser = new Parser();
        this.sessionController = sessionController;
        this.loginService = new LoginService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> data = parser.parseFormData(exchange);
            String email = data.get("email");
            String password = data.get("password");

            User user = loginService.getUserByEmailAndPassword(email, password);
            UUID uuid = UUID.randomUUID();
            sessionController.sessions.put(uuid, user);
            System.out.println(uuid + ": " + user);

            loginService.updateSessionIdByEmailAndPassword(uuid, email, password);

            String response = mapper.writeValueAsString(user);

            //  In cookie we should set json with user token and role instead of email
            HttpCookie cookie = new HttpCookie("sessionId", uuid.toString());
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

            HttpHelper.sendResponse(exchange, response, 200);
        } catch (Exception e) {
            e.printStackTrace();
            HttpHelper.sendResponse(exchange, e.getMessage(), 500);
        }
    }
}
