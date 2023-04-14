Feature: Enter login details

  Scenario Outline: Successful login
    Given I start the application
    When I wait for 2000 milliseconds
    And I click email field
    And I enter valid email <email>
    And I close the keyboard
    And I click password field
    And I enter valid password <password>
    And I close the keyboard
    And I click sign in button
    Then I expect to see main content page
    Examples:
      | email          | password   |
      | test@gmail.com | test123321 |