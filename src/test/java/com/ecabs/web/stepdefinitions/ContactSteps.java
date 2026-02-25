package com.ecabs.web.stepdefinitions;

import com.ecabs.web.hooks.WebHooks;
import com.ecabs.web.pages.ContactPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactSteps {

    ContactPage formPage =
            new ContactPage(WebHooks.driver);

    @Given("I am on the Practice Form page")
    public void open_page() {
        formPage.open();
    }

    @When("I fill out the practice form with valid data")
    public void fill_form() {
        formPage.fill_form();
    }

    @When("I try to submit the form without filling mandatory fields")
    public void i_try_to_submit_the_form_without_filling_mandatory_fields() {
        formPage.submit();
    }

    @And("I submit the practice form")
    public void submit_form() {
        formPage.submit();
    }

    @Then("I should see the confirmation modal")
    public void validate_success() {
        String modalTitle = formPage.validate_success();
        assertEquals("Thanks for submitting the form", modalTitle);
    }

    @Then("the mandatory fields should be required")
    public void validate_mandatory_fields() {
        assertTrue(formPage.are_mandatory_fields_required());
    }

}
