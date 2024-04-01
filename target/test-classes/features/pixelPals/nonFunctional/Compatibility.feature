@compatibility @p1
Feature: Pixel Pals Compatibility Testing
#  ensures seamless functionality and consistent user experience across various web browsers, operating systems, and devices.

  @desktop
  Scenario Outline: Desktop Browser Compatibility Testing
    Given user on test environment
    When the user accesses the page using "<browser>" web browser
    Then the landing page should render correctly on each browser
    And all features and functionalities should work as expected without any layout issues
    Examples:
    |browser|
    |chrome |
    |firefox|
    |safari |
    |edge   |

  @mobile
  Scenario Outline: Mobile Browser Compatibility Testing
    Given a user accesses pixel pals from a "<os>" mobile device
    When the user uses "<browser>" mobile browsers (Chrome for Android, Safari for iOS)
    Then the landing page should display correctly on each mobile browser
    And all features and functionalities should be accessible and usable without any display or functionality issues
    Examples:
      |os     |browser|
      |android|chrome |
      |ios    |firefox|
      |ios    |safari |
      |android|edge   |

  @os
  Scenario Outline: Operating System Compatibility Testing
    Given a user opens the landing page on a desktop computer
    When the user accesses the page using "<os>" operating system
    Then the landing page should render correctly on each operating system
    And all features and functionalities should work as expected without any compatibility issues
    Examples:
    |os|
    |windows|
    |mac    |
    |linux  |

  @device
  Scenario Outline: Device Compatibility Testing
    Given a user accesses the landing page from "<device>"
    When the user interacts with the page
    Then the landing page should display correctly on each device
    And all features and functionalities should be accessible and usable without any display or functionality issues
    Examples:
    |device|
    |desktop|
    |laptop |
    |tablet |
    |smartphone|