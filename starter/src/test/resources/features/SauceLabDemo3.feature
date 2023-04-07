Feature: SauceLab Demo Native

  @loginTestNative
  Scenario Outline: Successful login native
    Given app state is running in foreground
    When User opens native menu
    Then User verifies native menu is opened

    When User selects login from native menu
    Then Login native screen is displayed

#    When User logs in with username "<username>" and password "<password>" on the native login screen
#    Then User will not see login screen


    Examples:
      | username        | password |
      | bob@example.com | 10203040 |
      | bob@example.com | 10203040 |

  @changeContextToWebview
  Scenario Outline: Change context to webview
    Given app state is running in foreground
    When User opens native menu
    Then User verifies native menu is opened

#    Then Webview selection is displayed
#    When User enters "https://www.saucedemo.com" in webview selection and clicks enter

    When Context is changed to webview

#    When User logs in with username "<username>" and password "<password>" on the webview login screen




    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |


