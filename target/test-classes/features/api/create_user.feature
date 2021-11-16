@api
Feature: User creation verification

  Scenario: Create user - happy path
    Given the application is up and running
    When I create users with next parameters:
      | name           | job                      |
      | John Doe       | Test automation engineer |
      | Hawer Dretwota | QA                       |
      | Wziut Trudek   | Dev                      |
      | Den Tupa       | BA                       |
    Then response returns user id
