@tutorial
Feature: Pixel Pals Tutorial

  @timeOfDeath
  Scenario Outline: Time of Death
    Given user on test environment
    When user confirms "<pixelPal>" pixel pal
    Then time of Death tutorial is displayed
    Examples:
      |pixelPal|
      |boremon |
      |borg    |
      |byrus   |
      |foxphin |