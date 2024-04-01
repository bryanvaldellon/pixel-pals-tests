@landingPage @ui @p0
Feature: Pixel Pals Landing Page

  @launch
  Scenario: Launch Pixel Pals
    Given user on test environment
    When user on "PIXEL_PALS" application
    Then pixel pals app is displayed

  @functional @loading
  Scenario Outline: Loading the Landing Page
    Given a user on "PIXEL_PALS" application
    When the user navigates to the pixel pals website url
    Then the section "<section>" loads successfully
    Examples:
    |section            |
    |pixel pals logo    |
    |get started        |
    |current prize      |
    |volume             |
    |prize pool label   |
    |version            |
    |full screen toggle |
