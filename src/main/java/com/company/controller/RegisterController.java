package com.company.controller;

import com.company.dao.UserDao;
import com.company.helpers.HttpHelper;
import com.company.helpers.Parser;
import com.company.model.ModuleType;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.company.model.user.UserFactory;
import com.company.service.RegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class RegisterController implements HttpHandler {

    private Parser parser;
    private UserDao userDao;
    private ObjectMapper mapper;
    private RegisterService registerService;

    public RegisterController() {
        this.parser = new Parser();
        this.userDao = new UserDao();
        this.mapper = new ObjectMapper();
        this.registerService = new RegisterService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> formData = parser.parseFormData(exchange);

            UserFactory userFactory = new UserFactory();
            User user = userFactory.create(Integer.parseInt(formData.get("typeId")));

            user = registerService.createUserData(user,
                    formData.get("firstName"),
                    formData.get("lastName"),
                    Integer.parseInt(formData.get("typeId")),
                    formData.get("phoneNumber"),
                    formData.get("email"),
                    formData.get("password"),
                    Boolean.parseBoolean(formData.get("isActive")));

            if (user instanceof Student) {
                user = registerService.createAdditionalStudentData(user,
                        ModuleType.valueOf(formData.get("moduleType")),
                        Integer.parseInt(formData.get("experienceLevel")),
                        Integer.parseInt(formData.get("balance")));
            }

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
