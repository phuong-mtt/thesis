@sanity @buyProduct
Feature: Buy Product
  Background:
    Given Users login with valid credentials
    And Users login successfully

  @positive
  Scenario: [Positive] Buy products successfully
    Given Users click product to buy
#    When Users choose type of product
#    And Users click Buy Now button
#    Then Users should see correct private information
#    Then Users enter valid credentials
#    When Users click to choose products to buy
#    Then Users should see the correct total price
#    When Users click Payment button
#    Then Users should see order successfully message


#  @negative
#  Scenario: [Negative] Buy products unsuccessfully because of out of stock
#    Given Users click product which is out of stock
#    When Users click Buy button
#    Then Users should see error message that inform them products out of stock

