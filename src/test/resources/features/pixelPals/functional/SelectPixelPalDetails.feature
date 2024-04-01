@details
Feature: Select Pixel Pal Details

  @details
  Scenario Outline: View Pixel Pal Details
    Given a user is on the "Choose Pixel Pal" screen
    When the user clicks on a Pixel Pal option
    Then the screen should display information about "<informationLabel>" of Pixel Pal
    Examples:
    |informationLabel|
    |name            |
    |tribe           |
    |description     |
    |coins           |
    |gifts           |

  @confirm
  Scenario Outline: Confirm Pixel Pal Selection
    Given a user is on the "<pixelPal>"details screen
    When the user confirms a Pixel Pal
    Then the selected Pixel Pal should be your starting companion
    Examples:
      |pixelPal|
      |boremon |
      |borg    |
      |byrus   |
      |foxphin |

  @change
  Scenario Outline: Change Pixel Pal Selection
    Given a user is on the "<pixelPal>"details screen
    When the user change a Pixel Pal
    Then the user is navigated back to pixel pals selection screen
    Examples:
      |pixelPal|
      |boremon |
      |borg    |
      |byrus   |
      |foxphin |


  @image
  Scenario Outline: View Character Image
    Given a user is on the character details screen
    When the user views the "<pixelPal>" details
    Then the screen should display a clear and accurate image of the character
    Examples:
      |pixelPal|
      |boremon |
      |borg    |
      |byrus   |
      |foxphin |
