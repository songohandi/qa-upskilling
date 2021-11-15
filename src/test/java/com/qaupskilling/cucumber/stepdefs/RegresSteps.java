package com.qaupskilling.cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RegresSteps {

    Logger logger = LoggerFactory.getLogger(RegresSteps.class);

    private static final String HOST = "https://reqres.in/api";
    private static final String USER_SERVICE_ENDPOINT = "/users";
    private static final String AUTHN_SERVICE_ENDPOINT = "/register";
    private static final String SEPARATOR = "/";

    private ValidatableResponse response;

    @Given("user service is up and running")
    public void user_service_is_up_and_running() {
        System.out.println("User service is up and running");
        //logger.info("User service is up and running");
    }

    @When("I fetch all users from page {int}")
    public void iFetchAllUsersFromPage(int page) {
        response = given()
                .contentType(ContentType.JSON).param("page", page)
                .when()
                .get(HOST + USER_SERVICE_ENDPOINT )
                .then()
                .log()
                .body();
    }

    @Then("user list contains {int} users")
    public void userListContainsUsers(int numOfUsers) {
        int actualNumOfUsers = response
                .extract()
                .path("per_page");
        List<Map<String, String>> users = response
                .extract()
                .path("data");
        Assertions.assertEquals(numOfUsers, users.size());
    }

    @When("I fetch the information about user with id {int}")
    public void i_fetch_the_information_about_user_with_id(Integer id) {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(HOST + USER_SERVICE_ENDPOINT + SEPARATOR + id)
                .then();
        response
                .log()
                .body(); //.all();
    }
        @Then("user has a email {string}")
        public void user_has_a_email(String expectedEmail) {
            response.assertThat()
                    .statusCode(200)
                    .body("data.email", equalTo(expectedEmail));
            //String actualEmail = response.extract().body().path("data.email");
            //Map<String, String> user = response.extract().body().path("data");
            //Assertions.assertEquals(expectedEmail, actualEmail);
        }


    @Given("the application is up and running")
    public void theApplicationIsUpAndRunning() {
        //System.out.println("Application is up and running");
        logger.info("Application is up and running");
    }

//    @When("user registers with email {string} and password {string}")
//    public void userRegistersWithEmailAndPassword(String email, String password) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("email", email);
//        body.put("password", password);
//        response = given().contentType(ContentType.JSON).body(body)
//                .when().post(HOST + AUTHN_SERVICE_ENDPOINT).then().log().body();
//    }

    @Then("registration have been completed")
    public void registrationHaveBeenCompleted() {
        response.assertThat().statusCode(200);
    }

    @Then("registration have not been completed")
    public void registrationHaveNotBeenCompleted() {
        response.assertThat().statusCode(400);
    }

    @Then("registration {string} completed")
    public void registrationCompletionStatus(String string) {
        if (string.equals("have been"))
            response.assertThat().statusCode(200);
        else if (string.equals("have not been"))
            response.assertThat().statusCode(400);
    }

    @When("user registers with email {string} and password {string}")
    public void userRegistersWithEmailAndPassword(String email, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        response = given().contentType(ContentType.JSON).body(body)
                .when().post(HOST + AUTHN_SERVICE_ENDPOINT).then().log().body();
    }
}


//Refactor register.feature to use scenario outline