package com.company;

import com.company.controller.ArtifactController;
import com.company.controller.LoginController;
import com.company.controller.QuestController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        server.createContext("/login", new LoginController());
        server.createContext("/quest", new QuestController());
        server.createContext("/artifact", new ArtifactController());
        server.setExecutor(null);

        server.start();

        System.out.println("Server started at " + server.getAddress().getPort());
    }
}
