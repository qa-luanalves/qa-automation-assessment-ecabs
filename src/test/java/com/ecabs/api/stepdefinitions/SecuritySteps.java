package com.ecabs.api.stepdefinitions;

import com.ecabs.context.SharedResponse;
import io.cucumber.java.en.*;

import static io.restassured.RestAssured.given;

import static com.ecabs.api.config.ApiConfig.*;

public class SecuritySteps {

    @When("I request the current weather with invalid API key for {string}")
    public void request_weather_with_invalid_api_key(String city) {

        SharedResponse.response = given()
                .baseUri(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", "INVALID_KEY")
                .when()
                .get("/weather");
    }

    @When("I request the current weather without API key for {string}")
    public void request_weather_without_api_key(String city) {

        SharedResponse.response = given()
                .baseUri(BASE_URL)
                .queryParam("q", city)
                .when()
                .get("/weather");
    }
}
