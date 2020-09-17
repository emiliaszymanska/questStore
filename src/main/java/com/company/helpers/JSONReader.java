package com.company.helpers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONReader {

    public static Map<String, String> read() {
        Map<String, String> dbData = new HashMap<>();
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/dbdata.json"));

            dbData.put("url", (String) jsonObject.get("url"));
            dbData.put("user", (String) jsonObject.get("user"));
            dbData.put("password", (String) jsonObject.get("password"));

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dbData;
    }
}
