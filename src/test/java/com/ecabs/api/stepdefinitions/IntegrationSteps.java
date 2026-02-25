package com.ecabs.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import static com.ecabs.api.config.ApiConfig.*;

public class IntegrationSteps {

    private Response weatherResponse;
    private Response forecastResponse;

    private double currentTemp;
    private double todayForecastTemp;

    @When("I retrieve the current weather and forecast for {string}")
    public void retrieve_weather_and_forecast(String city) {

        weatherResponse = given()
                .baseUri(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .when()
                .get("/weather");

        forecastResponse = given()
                .baseUri(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .when()
                .get("/forecast");

        currentTemp = weatherResponse.jsonPath().getDouble("main.temp");

        List<Map<String, Object>> forecastList =
                forecastResponse.jsonPath().getList("list");

        String todayDate = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (Map<String, Object> item : forecastList) {
            String dateTime = (String) item.get("dt_txt");
            if (dateTime.startsWith(todayDate)) {

                Map<String, Object> main =
                        (Map<String, Object>) item.get("main");

                todayForecastTemp = Double.parseDouble(main.get("temp").toString());
                break;
            }
        }
    }

    @Then("today's forecast temperature should be close to current weather temperature")
    public void validate_temperature_consistency() {
        double difference = Math.abs(currentTemp - todayForecastTemp);

        assertTrue("Temperature difference too high: " + difference,
                difference <= 3.0);
    }

}
