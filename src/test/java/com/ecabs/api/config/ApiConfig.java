package com.ecabs.api.config;

public class ApiConfig {

    public static final String BASE_URL = System.getProperty("baseUrl","https://api.openweathermap.org/data/2.5");

    public static final String API_KEY =
            System.getProperty("apiKey");
}
