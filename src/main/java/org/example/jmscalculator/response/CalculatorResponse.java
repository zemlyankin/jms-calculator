package org.example.jmscalculator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatorResponse {
    private Integer result;

    //default constructor
    public CalculatorResponse(){
        super();
    }
}
