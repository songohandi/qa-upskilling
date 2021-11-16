@api
Feature: Register user

  Scenario Outline: Given User have been registered successfully
    Given the application is up and running
    When user registers with email "<email>" and password "<password>"
    Then registration "<success>" completed

    Examples:
      | email              | password    | success       |
      | eve.holt@reqres.in | machine gun | have been     |
      | eve.holt@reqres.in |             | have not been |
      |                    | machinegun  | have not been |