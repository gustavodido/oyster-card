Feature: Oyster Card Problem

  Scenario: User successfully take a set of trips
    Given the user Gustavo has loaded £30.00 in his card
    When he passes through the inward barrier at the Holborn station
    And he takes a train
    And he swipes out at the Earl's Court
    And he passes through the inward barrier at the Earl's Court station
    And he takes a bus
    And he swipes out at the Chelsea
    And he passes through the inward barrier at the Earl's Court station
    And he takes a train
    And he swipes out at the Hammersmith
    Then his card balance is £23.70