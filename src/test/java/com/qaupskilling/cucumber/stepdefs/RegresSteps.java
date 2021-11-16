package com.qaupskilling.cucumber.stepdefs;

import com.qaupskilling.cucumber.model.Registration;
import com.qaupskilling.cucumber.model.User;
import com.qaupskilling.cucumber.model.UserResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegresSteps {

    Logger logger = LoggerFactory.getLogger(RegresSteps.class);

    private static final String HOST = "https://reqres.in/api";
    private static final String USER_SERVICE_ENDPOINT = "/users";
    private static final String AUTHN_SERVICE_ENDPOINT = "/register";
    private static final String LOGIN_SERVICE_ENDPOINT = "/login";

    private static final String SEPARATOR = "/";

    private ValidatableResponse response;
    List<String> ids = new ArrayList<>();

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
        //response.assertThat().body("data.email", equalTo(expectedEmail)); <-- build-in assertions
        //String actualEmail = response.extract().body().path("data.email"); // extract only email
        UserResponse user = response.extract().body().jsonPath().getObject("data", UserResponse.class);
        //Map<String, String> user = response.extract().body().path("data"); <--- extract user as a map
        Assertions.assertEquals(expectedEmail, user.getEmail());
    }


    @Given("the application is up and running")
    public void theApplicationIsUpAndRunning() {
        //System.out.println("Application is up and running");
        logger.info("Application is up and running");
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
//        Map<String, Object> body = new HashMap<>();
//        body.put("email", email);
//        body.put("password", password);
        Registration body = new Registration(email, password);
        response = given().contentType(ContentType.JSON).body(body)
                .when().post(HOST + AUTHN_SERVICE_ENDPOINT).then().log().body();
    }

    @When("I create (user|users) with next parameters:")
    public void iCreateUserWithNextParameters(DataTable table) {
        var usersData = table.asMaps();
        createUsers(usersData);
    }

    private void createUsers(List<Map<String, String>> usersData) {
        for (Map<String, String> userData: usersData) {
            User newUser = User.fromMap(userData);
            String id = given().contentType(ContentType.JSON).body(newUser)
                    .when().post(HOST+USER_SERVICE_ENDPOINT)
                    .then().log().body().extract().path("id");
            ids.add(id);
        }
    }

    @Then("response returns user id")
    public void responseReturnsUserId() {
        Assertions.assertTrue(ids.size() > 0);
    }

    @When("User is logging using {string} and {string}")
    public void userIsLoggingUsingAnd(String email, String password) {
        Registration body = new Registration(email, password);
        response = given().contentType(ContentType.JSON).body(body)
                .when().post(HOST + LOGIN_SERVICE_ENDPOINT).then().log().body();
    }

    @Then("Login is {string}")
    public void loginStatus(String string) {
        String token = response.extract().path("token");

        if (string.equals("unsuccessful")){
            logger.info("Login unsuccessful");
            response.assertThat().statusCode(400);
            String error = response.extract().path("error");
            Assertions.assertTrue(error.equals("user not found"));
        }else if(string.equals("successful")){
            logger.info("Login successful");
            response.assertThat().statusCode(200);
            Assertions.assertTrue(!token.isEmpty());
        }
    }
}


//Refactor register.feature to use scenario outline