@login @p1
Feature: Login Page Usability Testing

  @field @p0
  Scenario Outline: Navigation to Login Page
    Given a user accesses the Pixel Pals website
    When the user clicks on the "get started" button
    Then the user should be directed to the login page
    And the login page should display "<field>" fields
    Examples:
    |field          |
    |login label    |
    |pixel pals logo|
    |email address  |
    |send code      |
    |google         |
    |facebook       |
    |apple          |
    |discord        |

  @input @negative @error
  Scenario: Input Field Validation
    Given a user is on the login page
    When the user attempts to submit the login form without entering any credentials
    Then appropriate error messages should be displayed indicating required fields

  @success @positive @p0
  Scenario: Successful Login
    Given a user is on the login page
    When the user enters valid credentials
    And clicks on the "Login" button
    Then the user should be redirected to the dashboard or homepage

  @forgotPassword @toConfirm
  Scenario: Forgotten Password
    Given a user is on the login page
    When the user clicks on the "Forgot Password" link
    Then the user should be directed to the password recovery page

  @session @sameSession
  Scenario: Remember Me Functionality
    Given a user accesses the login page
    When the user log in
    Then the user's session should persist across same browser sessions

  @session @differentSession
  Scenario: Different browser session
    Given a user accesses the login page
    When the user log in in different browser
    Then the user's session should not persist across different browser sessions

  @socialMedia
  Scenario Outline: Social Media for <socialMedia> Login Integration
    Given a user is on the login page
    When the user log in using "<socialMedia>" accounts
    Then the user should be redirected to the corresponding social media login page
    Examples:
    |socialMedia|
    |google         |
    |facebook       |
    |apple          |
    |discord        |