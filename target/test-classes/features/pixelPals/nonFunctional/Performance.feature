@performance
Feature: Pixel Pals Performance Testing
#  ensures that it loads quickly and functions smoothly, even under heavy traffic conditions,
#  ensuring a seamless and responsive user experience.

  @load
  Scenario: Load Time Testing
    Given user on test environment
    When the user navigates to the Pixel Pals website URL
    Then the landing page should load within less than "1" second

  @scalability
  Scenario: Scalability Testing
    Given test environment is accessed by multiple users simultaneously
    When the number of concurrent users increases gradually
    Then the landing page response time should remain stable within acceptable limits
    And the server should be able to handle the increased load without degradation in performance

  @stress
  Scenario Outline: Stress Testing
    Given user on test environment
    When the user navigates to the Pixel Pals website URL
    Then the landing page should be able to handle "<users>" concurrent users without crashing or significant slowdowns
    And the landing page response time should remain within acceptable limits even under heavy load
    Examples:
    |users|
    |128  |
    |512  |
    |1024  |
    |2048  |
    |4096  |

  @resource
  Scenario Outline: Resource Utilization Testing
    Given user on test environment
    When the page finishes loading
    Then the landing page should not consume excessive system resources for "<systemResource>"
    And the page should be optimized for efficient resource utilization
    Examples:
    |systemResource|
    |cpu           |
    |memory        |
    |bandwidth     |