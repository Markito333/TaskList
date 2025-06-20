Feature: Task Management
  Scenario: Add a new task
    Given I am on the task list page
    When I click the add button
    And I enter "New task" in the editor
    And I click the save button
    Then I should see "New task" in the task list