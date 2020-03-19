package org.example.jmscalculator.request;

import lombok.Data;

import java.util.List;

@Data
public class CalculatorRequest {
    private Operation operator;
    private List<Integer> operands;
}
