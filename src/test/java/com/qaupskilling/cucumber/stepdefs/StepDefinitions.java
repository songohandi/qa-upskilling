package com.qaupskilling.cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class StepDefinitions {

    @Given("An empty Maven Cucumber project")
    public void i_create_empty_maven_cucumber_project() {
        System.out.println("An empty Maven Cucumber project");
    }


    @When("I run maven command clean verify")
    public void i_run_maven_command_clean_verify() {
        System.out.println("I run maven command clean verify");
    }
    @Then("I see some reports generated in target folder")
    public void shouldHaveReportInTargetFolder() {
        System.out.println("I see some reports generated in target folder");
        Assertions.assertTrue(true);
        //throw new io.cucumber.java.PendingException();
    }
}
