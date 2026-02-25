package com.ecabs.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = "com.ecabs.api",
        plugin = {
                "pretty",
                "html:target/cucumber-api-report.html",
                "json:target/cucumber-api-report.json"
        }
)

public class ApiTestRunner {
}