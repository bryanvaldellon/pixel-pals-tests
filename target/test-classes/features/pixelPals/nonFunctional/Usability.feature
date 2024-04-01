@usability
Feature: Pixel Pals Usability Testing
#  evaluates its ease of use and effectiveness in fulfilling user needs, ensuring intuitive navigation,
#  clear communication, and seamless interaction across different devices and user scenarios.

  @desktopBrowser @desktop
  Scenario Outline: <os> Desktop Browser Testing
    Given a user opens pixel pals on a "<os>" desktop browser
    When the user resizes the browser window to different screen sizes
    Then the landing page content should adjust dynamically to fit the screen
    And all elements should remain accessible and usable without horizontal scrolling
    Examples:
    |os     |
    |windows|
    |mac    |
    |linux  |

  @mobileBrowser @mobile
  Scenario Outline: Mobile Compatibility Testing
    Given a user opens pixel pals from a "<os>" mobile browser
    When the user interacts with the page
    Then the landing page should render correctly on mobile screens
    And all features and functionalities should be easily accessible and usable on touch screens
    Examples:
    |os     |
    |android|
    |ios    |

  @readability
  Scenario: Content Readability Testing
    Given a user opens pixel pals
    When the user views the content
    Then the text should be easy to read with appropriate font size and contrast
    And important information should be highlighted or emphasized for better readability

  @accessibility
  Scenario: Accessibility Testing
    Given a user opens the landing page
    When the user accesses the page using a screen reader or keyboard navigation
    Then all content and functionalities should be accessible and usable without reliance on mouse interaction
    And the page should comply with accessibility standards such as WCAG (Web Content Accessibility Guidelines)