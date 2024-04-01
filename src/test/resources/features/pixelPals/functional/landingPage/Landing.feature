@landingPage @ui @p1
Feature: Pixel Pals Landing Page

  @functional
  Scenario Outline: Loading the Landing Page
    Given a user on test environment
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
