package com.company.controller;

import com.company.dao.StudentDao;
import com.company.dao.UserDao;
import com.company.helpers.Parser;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Collections;
import java.util.Map;

public class LoginController implements HttpHandler {

    private ObjectMapper mapper;

    public LoginController() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            Map<String, String> data = Parser.parseFormData(br.readLine());
            String email = data.get("email");
            String password = data.get("password");

            UserDao userDao = new UserDao();
            User user = userDao.getByEmailPassword(email, password);

            if (user instanceof Student) {
                StudentDao studentDao = new StudentDao();
                user = studentDao.getStudentByIdWithAdditionalData(user.getId());
//                ((Student) user).setModuleType();
            }
            String response = mapper.writeValueAsString(user);

            //  In cookie we should set json with user token and role instead of email
            HttpCookie cookie = new HttpCookie("user", response);
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            exchange.getResponseHeaders().put("Access-Control-Allow-Credentials", Collections.singletonList("true"));

            sendResponse(response, exchange, 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(e.getMessage(), exchange, 500);
        }
    }

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
        }
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
