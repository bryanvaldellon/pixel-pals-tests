@select
Feature: Select Pixel Pal

  @display @available
  Scenario: Display Available Pixel Pals
    Given a user is logged in to the Pixel Pals website
    When the user navigates to the "Select Pixel Pal" section
    Then the screen should display available Pixel Pal options for selection

  @select
  Scenario Outline: Select Pixel Pal
    Given a user is on the "Choose Pixel Pal" screen
    When the user select "<pixelPal>" Pixel Pal
    Then the selected Pixel Pal should be highlighted or indicated as chosen
    Examples:
    |pixelPal|
    |boremon |
    |borg    |
    |byrus   |
    |foxphin |

  @mint
  Scenario Outline: Mint a Pixel Pal
    Given user on pixel pal selection page
    When user mint "<pixelPal>" pixel pal
    Then pixel pal details are displayed
    Examples:
      |pixelPal|
      |boremon |
      |borg    |
      |byrus   |
      |foxphin |

  @details
  Scenario: View Pixel Pal Details
    Given a user is on the "Choose Pixel Pal" screen
    When the user clicks on a Pixel Pal option
    Then the screen should display detailed information about the selected Pixel Pal, such as name, description, and price

  Scenario: Add Pixel Pal to Cart
    Given a user is on the "Choose Pixel Pal" screen
    When the user selects a Pixel Pal and clicks on the "Add to Cart" button
    Then the selected Pixel Pal should be added to the user's shopping cart
