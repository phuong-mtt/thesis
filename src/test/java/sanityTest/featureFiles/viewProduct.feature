@sanity @viewProduct
Feature: View Product
  Scenario: [Positive] Search and view detailed information successfully
    Given Users search for product
    When Users click product
    Then User should see the detailed information of product
