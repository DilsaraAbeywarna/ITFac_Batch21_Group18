Feature: Cancel Sales Navigation

  Scenario: Verify cancel button navigation
    Given Admin is logged in
    And Admin is on the sales page
    When Admin clicks Sell Plant
    And Admin clicks Cancel button
    Then Admin should be redirected to sales list page
