@localization
Feature: Pixel Pals Localization Testing
#  ensures that content, language, currency, date formats, and cultural sensitivities are appropriately
#  adapted for different regions and languages, providing users with a tailored and culturally relevant experience.

  @language
  Scenario: Language Selection Testing
    Given a user accesses the Pixel Pals website
    When the user selects a different language from the language dropdown menu
    Then the website content should be translated into the selected language
    And all text, labels, and messages should be displayed in the chosen language

  @currency
  Scenario: Currency Conversion Testing
    Given a user accesses the Pixel Pals website
    When the user selects a different currency from the currency dropdown menu
    Then the product prices should be displayed in the selected currency
    And prices should be converted accurately based on the current exchange rates

  @date @time
  Scenario: Date and Time Format Testing
    Given a user accesses the Pixel Pals website
    When the user selects a different locale or region
    Then date and time formats should be displayed according to the selected locale
    And formats should adhere to local conventions (e.g., DD/MM/YYYY vs. MM/DD/YYYY)

  @addressFormat
  Scenario: Address Format Testing
    Given a user accesses the Pixel Pals website
    When the user selects a different country or region
    Then address formats (e.g., postal codes, street addresses) should conform to local standards
    And address fields should be labeled appropriately based on regional preferences

  @cultural
  Scenario: Cultural Sensitivity Testing
    Given a user accesses the Pixel Pals website
    When the user views product descriptions, images, and marketing content
    Then content should be culturally sensitive and appropriate for the selected locale
    And images and symbols should be relevant and respectful of local customs and traditions