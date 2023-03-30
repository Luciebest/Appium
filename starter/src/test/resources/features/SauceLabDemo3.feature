Feature: SauceLab Demo Native

  @loginTest2
  Scenario Outline: Successful login
    Given app state is running in foreground
    When User opens native menu
    Then User verifies native menu is opened

    When User selects login from native menu
    Then Login native screen is displayed

#    When I see webview page


    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |


