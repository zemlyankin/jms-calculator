package org.example.jmscalculator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;

    //default constructor
    public ErrorResponse(){
        super();
    }
}
