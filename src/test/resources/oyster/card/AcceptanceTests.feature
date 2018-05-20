Feature: Oyster Card Problem

  Background:

  Scenario: User successfully take a set of trips
    Given the user Gustavo has loaded £30.00 in his card
    When he passes through the inward barrier at the Holborn station
    And he takes a train
    And he swipes out at the Earl's Court
    Then his card balance is £27.50