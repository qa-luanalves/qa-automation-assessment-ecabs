package com.ecabs.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "com.ecabs.web",
        plugin = {
                "pretty",
                "html:target/cucumber-web-report.html"
        }
)

public class WebTestRunner {
}
