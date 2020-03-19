package org.example.jmscalculator.service;

import org.example.jmscalculator.request.CalculatorRequest;
import org.example.jmscalculator.response.CalculatorResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.function.BinaryOperator;

@Service
public class CalculatorService {
    public CalculatorResponse calculate(CalculatorRequest request) {
        if (request.getOperator() == null) {
            throw new IllegalArgumentException("operation is not set");
        }

        if (CollectionUtils.isEmpty(request.getOperands())) {
            throw new IllegalArgumentException("operands are not set");
        }

        BinaryOperator<Integer> function = null;
        switch (request.getOperator()) {
            case add:
                function = (a, b) -> a + b;
                break;
            case sub:
                function = (a, b) -> a - b;
                break;
            case mul:
                function = (a, b) -> a * b;
                break;
            case div:
                function = (a, b) -> a / b;
                break;
        }
        Integer result = request.getOperands().stream().reduce(function).get();
        return new CalculatorResponse(result);
    }
}
