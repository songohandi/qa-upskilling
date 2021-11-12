# background
Feature: Landing page basic verifications
  As an admin Ed
  In order to have all things set up
  I need to verify basic elements on the main page

  Background:
  Given "John" on the shop landing page
  When he accepts privacy policy with default settings

  Scenario: Populars area verification
    Then "Popular list" is visible
    And "Signing button" is visible

  Scenario: Shop selections area verification
    Then "Selections area" is visible
    And "Subscribe area" is visible