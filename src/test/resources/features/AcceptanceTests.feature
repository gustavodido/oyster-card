Feature: Oyster Card Problem

  Scenario: User successfully take a set of trips
    Given the user Gustavo has loaded £30.00 in the card
    When passes through the inward barrier at the Holborn station
    And takes a train
    And swipes out at the Earl's Court station
    And passes through the inward barrier at the Earl's Court station
    And takes a bus
    And swipes out at the Chelsea station
    And passes through the inward barrier at the Earl's Court station
    And takes a train
    And swipes out at the Hammersmith station
    Then the card balance is £23.70

  Scenario: User forgets to swipe out the card and have maximum fare discounted
    Given the user Tuany has loaded £30.00 in the card
    When passes through the inward barrier at the Holborn station
    And takes a train
    And forgets to swipes out at the Earl's Court station
    And passes through the inward barrier at the Wimbledon station
    And takes a bus
    And forgets to swipes out at the Hammersmith station
    Then the card balance is £23.60

  Scenario: User does have sufficient funds to start the journey
    Given the user Eluisete has loaded £3.00 in the card
    When passes through the inward barrier at the Wimbledon station
    Then the barrier does not open because of insufficient funds
