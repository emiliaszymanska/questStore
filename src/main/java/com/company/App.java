package com.company;

import com.company.controller.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        server.createContext("/register", new RegisterController());
        server.createContext("/login", new LoginController(new SessionController()));
        /*
            http://localhost:8001/student/wallet
            http://localhost:8001/student/quests
            http://localhost:8001/student/store
            http://localhost:8001/student
         */
        server.createContext("/student", new StudentController());
        server.createContext("/quest", new QuestController());
        server.createContext("/artifact", new ArtifactController());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started at " + server.getAddress().getPort());
    }
}
