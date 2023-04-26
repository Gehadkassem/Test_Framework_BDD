Feature: Subscription Packages Validation

  Scenario: Validate Subscription Packages for SA
    Given I am on the subscription page
    When I select "SA" country
    Then I should see the subscription packages with valid type, price and currency

  Scenario: Validate Subscription Packages for Kuwait
    Given I am on the subscription page
    When I select "Kuwait" country
    Then I should see the subscription packages with valid type, price and currency

  Scenario: Validate Subscription Packages for Bahrain
    Given I am on the subscription page
    When I select "Bahrain" country
    Then I should see the subscription packages with valid type, price and currency
