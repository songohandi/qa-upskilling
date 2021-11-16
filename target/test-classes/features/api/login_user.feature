Feature: user login

  Scenario: Login happy path
    Given the application is up and running
    When User is logging using "eve.holt@reqres.in" and "cityslicka"
    Then Login is "successful"


  Scenario: Login rainy day scenario
    Given the application is up and running
    When User is logging using "me@some.com" and "cityslicka"
    Then Login is "unsuccessful"