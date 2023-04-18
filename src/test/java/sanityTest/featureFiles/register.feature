@sanity @register
Feature: Register account

  Background:
  Given User is in homepage and do not login system

  @positive
  Scenario: [Positive] User register successfully with valid credentials
    Given User in register page
    When User enter valid user name
    When User enter valid password
    Then User click Register button
    Then User should see successful message