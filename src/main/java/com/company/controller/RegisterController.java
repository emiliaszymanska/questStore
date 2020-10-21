package com.company.controller;

import com.company.dao.UserDao;
import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.company.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.util.Map;

public class RegisterController implements HttpHandler {

    private ObjectMapper mapper;
    private Parser parser;
    private LoginService loginService;
    private UserDao userDao;

    public RegisterController() {
        this.mapper = new ObjectMapper();
        this.parser = new Parser();
        this.loginService = new LoginService();
        this.userDao = new UserDao();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> formData = parser.parseFormData(exchange);

            String email = formData.get("email");
            String password = formData.get("password");
            String type = formData.get("type");

//            this.id = builder.id;
//            this.firstName = builder.firstName;
//            this.lastName = builder.lastName;
//            this.typeId = builder.typeId;
//            this.phoneNumber = builder.phoneNumber;
//            this.email = builder.email;
//            this.password = builder.password;
//            this.isActive = builder.isActive;
            // getFromFormData
            // przekazac odpowiedni typ do factory i zwrocic odpowiedniego uzytkownika
            User user = new Student.Builder().build();
            user.setFirstName(formData.get("firstName"))
                    .setLastName(formData.get("lastName"))
                    .setTypeId(Integer.parseInt(formData.get("typeId")))
                    .setPhoneNumber(formData.get("phoneNumber"))
                    .setEmail(formData.get("email"))
                    .setPassword(formData.get("password"))
                    .setActive(Boolean.parseBoolean(formData.get("isActive")));

            System.out.println(user);
            userDao.insert(user);

            String response = mapper.writeValueAsString(user);

            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

            HttpHelper.sendResponse(exchange, response, 200);
        } catch (Exception e) {
            e.printStackTrace();
            HttpHelper.sendResponse(exchange, e.getMessage(), 500);
        }
    }
}
