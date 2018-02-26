Feature: Search feature

  @SmokeTests
  Scenario: Search For Specific Place in Google Maps
    Given I Have BaseURI with below parameters:
      | location | -33.8670522,151.1957362 |
      | radius   |                    5000 |
      | type     | restaurant              |
      | keyword  | cruise                  |
      | key      | YOUR_API_KEY            |
    When I want to "GET" Request
    Then I should verify "results.name" Should have "Cruise Bar, Restaurant & Events"
    And Verify status code is 200
    And Verify there are 2 results

  Scenario: Add a Place in Google Maps
    Given I Have BaseURI with Json file "Search.json"
    When I want to "POST" Request
    Then I verify that:
      | scope  | APP |
      | status | OK  |
    And Verify status code is 200
