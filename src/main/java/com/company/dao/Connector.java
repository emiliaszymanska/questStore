package com.company.dao;

import com.company.helpers.JSONReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

public class Connector {

    private Map<String, String> dbData = JSONReader.read();
    protected Connection connection;
    protected Statement statement;

    public void connect() {
        try {
            connection = DriverManager.getConnection(
                    dbData.get("url"),
                    dbData.get("user"),
                    dbData.get("password")
            );
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
