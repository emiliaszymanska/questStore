package com.company.controller;

import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.model.user.User;
import com.company.model.user.UserFactory;
import com.company.service.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class RegisterController implements HttpHandler {

    private Parser parser;
    private RegisterService registerService;

    public RegisterController() {
        this.parser = new Parser();
        this.registerService = new RegisterService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> formData = parser.parseFormData(exchange);

            UserFactory userFactory = new UserFactory();
            User user = userFactory.create(Integer.parseInt(formData.get("typeId")));
            String response = registerService.createNewUser(user, formData);

            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

            HttpHelper.sendResponse(exchange, response, 200);
        } catch (Exception e) {
            e.printStackTrace();
            HttpHelper.sendResponse(exchange, e.getMessage(), 500);
        }
    }
}
