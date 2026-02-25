package com.ecabs.web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ContactPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By email = By.id("userEmail");
    private By genderMaleInput = By.id("gender-radio-1");
    private By mobile = By.id("userNumber");
    private By submitButton = By.id("submit");
    private By successModalText = By.id("example-modal-sizes-title-lg");

    public void open() {
        driver.get("https://demoqa.com/automation-practice-form");
    }

    public void fill_form() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName))
                .sendKeys("Luan");

        driver.findElement(lastName).sendKeys("Alves");
        driver.findElement(email).sendKeys("luan@email.com");

        driver.findElement(genderMaleInput).click();
        driver.findElement(mobile).sendKeys("1234567890");

    }

    public void submit(){
        WebElement submit = wait.until(
                ExpectedConditions.presenceOfElementLocated(submitButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", submit);
    }

    public String validate_success() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successModalText));
        return driver.findElement(successModalText).getText();
    }

    private boolean is_required(By locator) {
        WebElement element = driver.findElement(locator);
        return element.getAttribute("required") != null;
    }

    public boolean are_mandatory_fields_required() {
        return is_required(firstName)
                && is_required(lastName)
                && is_required(mobile)
                && is_required(genderMaleInput);
    }

}
