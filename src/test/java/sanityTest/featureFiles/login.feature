@sanity @login
Feature: Login

  Background:
    Given Users are in login page

  @positive
  Scenario: [Positive] User login successfully with valid credentials
    When Users enter valid userName and password
    And Users click login button
    Then Users are in their previous page

  @negative
  Scenario: [Negative] User login unsuccessfully without registered username
    When Users enter invalid userName
    And Users enter password
    And Users click login button
    Then Users should see error message

  @negative
  Scenario: [Negative] User login unsuccessfully with incorrect credentials
    When Users enter correct userName and incorrect password
    And Users click login button
    Then Users should see error message
    When Users enter incorrect userName and correct password
    And Users click login button
    Then Users should see error message

  @negative
  Scenario: [Negative] User login unsuccessfully without required fields
    When Users enter login without username
    And Users click login button
    Then Users should see error message
    When Users enter login without password
    And Users click login button
    Then Users should see error message
