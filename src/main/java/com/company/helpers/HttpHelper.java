package com.company.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpHelper {

    public static void sendResponse(HttpExchange exchange, String response, int status) throws IOException {
        if (status == 200) {
            String origin = exchange.getRequestHeaders().getFirst("Origin");
            if (origin != null) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", origin);
            }
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
        }
        exchange.sendResponseHeaders(status, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
