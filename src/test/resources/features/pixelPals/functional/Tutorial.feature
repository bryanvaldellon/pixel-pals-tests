@tutorial
Feature: Pixel Pals Tutorial

  @timeOfDeath
  Scenario Outline: Time of Death
    Given user on test environment
    When user confirms "<pixelPal>" pixel pal
    Then "<tutorial>" tutorial is displayed
    Examples:
      |pixelPal|tutorial      |
      |boremon |time of death |
      |borg    |pet points    |
      |byrus   |pet level     |
      |foxphin |habitat value |
      |foxphin |pixel pass    |
      |foxphin |revival       |