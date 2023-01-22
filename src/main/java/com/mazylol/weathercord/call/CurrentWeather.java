package com.mazylol.weathercord.call;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mazylol.weathercord.models.current.CurrentRoot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.mazylol.weathercord.Bot.weatherApiKey;

public class CurrentWeather {
    public static CurrentRoot Get(String location, String unit) throws IOException, InterruptedException {
        JsonArray geocoderJson = JsonParser.parseString(Geocoder.Get(location)).getAsJsonArray();
        JsonObject geocoderJsonObject = geocoderJson.get(0).getAsJsonObject();

        double lat = geocoderJsonObject.get("lat").getAsDouble();
        double lon = geocoderJsonObject.get("lon").getAsDouble();

        URI url = URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=" + unit + "&appid=" + weatherApiKey);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(url)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // note to self, the has 1h and 3h reversed because I cannot serialize it due to variable naming conventions
        String cleanedResponse = getResponse.body().replaceAll("1h", "h1").replaceAll("3h", "h3");

        ObjectMapper om = new ObjectMapper();

        return om.readValue(cleanedResponse, CurrentRoot.class);
    }
}
