package com.company.controller;

import com.company.dao.StudentDao;
import com.company.model.Artifact;
import com.company.model.Quest;
import com.company.model.Wallet;
import com.company.model.user.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public class StudentController implements HttpHandler {

    private SessionController sessionController;
    private StudentDao studentDao;
    private Artifact artifact;
    private Wallet wallet;
    private Quest quest;

    public StudentController(SessionController sessionController) {
        this.sessionController = sessionController;
        studentDao = new StudentDao();
        artifact = new Artifact();
        wallet = new Wallet();
        quest = new Quest();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String url = exchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        for (int i = 0; i < actions.length; i++) {
            System.out.println(actions[i]);
        }

//        String action = actions.length == 2 ? "" : actions[2].matches("\\d+") ? "details" : actions[2];
        ObjectMapper mapper = new ObjectMapper();
        String response = "";

        try {
            switch (method) {
                case "GET":
                    switch (actions[2]) {
                        case "student-wallet":

                            break;
                        case "artifact":

                            break;
                        case "quest":
                            break;
                        case "profile":
                            break;
                    }
                    switch (actions[3]) {

                    }
                    /*
                    zalogowany student chce uzyskac informacje
                    -wallet -> balance, level, bought artifacts -> WalletController
                    -allQuests -> zawsze beda wszystkie dostepne z bazy danych -> QuestController
                    -allArtifacts -> wszystkie mozliwe artefakty do kupienia -> ArtifactController
                    -profile -> imie, nazwisko, mail, numer telefonu, moduł -> ProfileController
                     */
                    break;
                case "POST":
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

