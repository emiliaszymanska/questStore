package com.company.helpers;

public class ActionParser {

    public Actions fromURL(String url) {
        String[] URLComponents = url.split("/");

        return new Actions(URLComponents);
    }
}
