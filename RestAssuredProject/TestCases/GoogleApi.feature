Feature: Search feature

  Background: 
    Given I Have BaseURI

  @SmokeTests
  Scenario: Search For Specific Place in Google Maps
    When I want to "GET" Request and pass these parameters:
      | location | -33.8670522,151.1957362 |
      | radius   |                     500 |
      | type     | restaurant              |
      | keyword  | cruise                  |
      | key      | YOUR_API_KEY            |
    Then I should verify "results/geometry/name" Should be "Sydney Showboats"
