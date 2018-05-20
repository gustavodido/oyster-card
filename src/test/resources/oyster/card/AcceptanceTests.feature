Feature: Oyster Card Problem

  Background:
    Given The oyster card system has the stations created

  Scenario: User successfully take a set of trips
    Given the user Gustavo has loaded £30.00 in his card
    When he goes from Holborn to Earl's Court by train
    And he goes from Earl’s Court to Chelsea by bus
    And he goes from Earl’s court to Hammersmith by train
    Then his card balance is £27.00