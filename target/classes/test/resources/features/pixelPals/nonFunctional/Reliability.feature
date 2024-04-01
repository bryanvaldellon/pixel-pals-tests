@reliability
Feature: Pixel Pals Reliability Testing
#  ensures its stability and consistent functionality under normal and stressful conditions,
#  guaranteeing users uninterrupted access and smooth interactions.

  @session
  Scenario: Session Management Testing
    Given a user interacts with the Pixel Pals website
    When the user logs in and performs actions on the website
    Then the website should maintain the user's session securely
    And the user should remain logged in without unexpected session timeouts or disruptions

  @database
  Scenario: Database Reliability Testing
    Given the Pixel Pals website relies on a database for storing and retrieving data
    When the website experiences high traffic or concurrent user interactions
    Then the database should handle the load efficiently without data corruption or loss
    And database backups and redundancy measures should be in place to ensure data integrity and availability

  @recovery
  Scenario: Recovery Testing
    Given the Pixel Pals website encounters unexpected failures or crashes
    When the website is restarted or recovered
    Then the website should recover gracefully without data loss or corruption
    And the website's recovery process should be tested and documented for future reference