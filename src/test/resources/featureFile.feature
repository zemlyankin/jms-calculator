Feature: operation ADD

  Scenario Outline: add two integers
   #"add" can be changened to operations and add into the table
    When operation add
    And two operands are <value1> and <value2>
    And send message
    And get result
    Then result = <result>

    Examples:
   #positive
      | value1| value2| result |
      |  2    |   0   |    2   |
      |  2    |   3   |    5   |
      | -2    |  -4   |   -6   |
      | -2    |   0   |   -2   |
      | MAX_VALUE |   0   |  MAX_VALUE  |
      | MIN_VALUE |   -1   |  MAX_VALUE  |
      |  3    |       |    3   |
   #negative
      |  a    |   b   |  null  |
      | null  |   0   |  null  |
      | MAX_VALUE |   2   |  MAX_VALUE  |
   #special sign TO BE DONE
   #  | #  |   ?   |  null  |

  #NOTIES: additional scenarios for 3 (or empty) values can be added