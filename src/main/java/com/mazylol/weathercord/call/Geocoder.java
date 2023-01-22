package com.mazylol.weathercord.call;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.mazylol.weathercord.Bot.weatherApiKey;

public class Geocoder {
    public static String Get(String location) throws IOException, InterruptedException {
        URI url = URI.create("https://api.openweathermap.org/geo/1.0/direct?q=" + location + "&appid=" + weatherApiKey);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(url)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        return getResponse.body();
    }
}
