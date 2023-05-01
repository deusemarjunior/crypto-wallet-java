package com.cryptowallet.infrastructure.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestHandle {

    public String makeHttpGetRequest(String urlParam) throws Exception {
        try {
            URL url = new URL(urlParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return new String(connection.getInputStream().readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(String.format("Error http request from url %s",urlParam));
        }
    }
}
