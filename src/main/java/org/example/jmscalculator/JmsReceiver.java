package org.example.jmscalculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jmscalculator.request.CalculatorRequest;
import org.example.jmscalculator.response.CalculatorResponse;
import org.example.jmscalculator.response.ErrorResponse;
import org.example.jmscalculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "org.example.jms-calculator.request")
    public void receiveMessage(String message) {
        Object response;
        try {
            CalculatorRequest calculatorRequest = objectMapper.readValue(message, CalculatorRequest.class);
            response = calculatorService.calculate(calculatorRequest);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            response = new ErrorResponse(e.getMessage());
        }
        jmsTemplate.convertAndSend("org.example.jms-calculator.response", response);
    }
}
