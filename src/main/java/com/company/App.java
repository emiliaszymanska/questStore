package com.company;

import com.company.controller.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        server.createContext("/login", new LoginController(new SessionController()));
        server.createContext("/student", new StudentController(new SessionController()));
        server.createContext("/quest", new QuestController());

        server.createContext("/student-wallet", new ArtifactController());
        server.createContext("/artifact", new ArtifactController());
        server.createContext("/profile", new ProfileController());

        server.setExecutor(null);

        server.start();

        System.out.println("Server started at " + server.getAddress().getPort());
    }
}
