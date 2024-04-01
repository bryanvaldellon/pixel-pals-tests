@security
Feature: Pixel Pals Security Testing
#  evaluates its resilience against cyber threats, ensuring that sensitive data is protected

  @ssl @tls @encryption
  Scenario: SSL/TLS Encryption Testing
    Given a user opens the pixel pals
    When the user checks the URL in the browser's address bar
    Then the landing page should be served over HTTPS
    And the SSL/TLS certificate should be valid and up-to-date
    And all data transmission between the user's browser and the server should be encrypted

  @sqlInjection
  Scenario: SQL Injection Testing
    Given a user interacts with the login page input field
    When the user enters malicious SQL injection payloads
    Then the landing page should sanitize input data to prevent SQL injection attacks
    And the page should not execute any unauthorized database queries

  @xss
  Scenario: Cross-Site Scripting (XSS) Testing
    Given a user interacts with the login page input field
    When the user enters malicious XSS payloads
    Then the landing page should sanitize input data to prevent XSS attacks
    And the page should not execute any unauthorized scripts or code injected by the user

  @csrf
  Scenario: Cross-Site Request Forgery (CSRF) Testing
    Given a user interacts with forms or buttons on the landing page
    When the user submits a request with forged CSRF tokens
    Then the landing page should validate CSRF tokens for all state-changing requests
    And the page should reject requests with invalid or missing CSRF tokens

  @authentication @authorization
  Scenario: Authentication and Authorization Testing
    Given a user accesses restricted areas or functionalities on pixel pals
    When the user attempts to bypass authentication or access unauthorized resources
    Then the landing page should enforce proper authentication and authorization mechanisms
    And only authenticated users with appropriate permissions should be allowed access to restricted resources

  @error
  Scenario: Error Handling and Logging Testing
    Given user on test environment
    When the user triggers errors or exceptions
    Then the landing page should display appropriate error messages to the user
    And detailed error logs should be generated and monitored to detect and mitigate security incidents