package com.ecabs.api.stepdefinitions;

import com.ecabs.context.SharedResponse;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import static com.ecabs.api.config.ApiConfig.*;

public class WeatherSteps {

    @Given("the weather API is available")
    public void health_check() {
        SharedResponse.response =
                given()
                        .baseUri(BASE_URL)
                        .queryParam("q", "London")
                        .queryParam("appid", API_KEY)
                        .when()
                        .get("/weather");

        SharedResponse.response.then()
                .statusCode(200);
    }

    @When("I request the current weather for {string}")
    public void request_weather(String city) {
        SharedResponse.response = given()
                .baseUri(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .when()
                .get("/weather");
    }

    @When("I request the 5 day forecast for {string}")
    public void request_five_day_forecast(String city) {
        SharedResponse.response = given()
                .baseUri(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .when()
                .get("/forecast");
    }

    @Then("the response status should be {int}")
    public void validate_status(int statusCode) {
        assertEquals(statusCode, SharedResponse.response.getStatusCode());
    }

    @Then("the error message should be {string}")
    public void validate_error_message(String message) {
        assertEquals(message, SharedResponse.response.jsonPath().getString("message"));
    }

    @And("the forecast list should not be empty")
    public void validate_forecast_list() {
        List<Object> forecastList = SharedResponse.response.jsonPath().getList("list");
        assertFalse(forecastList.isEmpty());
    }
}
