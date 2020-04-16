package org.example.jmscalculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jmscalculator.request.CalculatorRequest;
import org.example.jmscalculator.response.ErrorResponse;
import org.example.jmscalculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
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
    public void receiveMessage(String body, @Header(JmsHeaders.CORRELATION_ID) String jmsCorrelationId) {
        Object response;
        try {
            CalculatorRequest calculatorRequest = objectMapper.readValue(body, CalculatorRequest.class);
            response = calculatorService.calculate(calculatorRequest);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            response = new ErrorResponse(e.getMessage());
        }
        jmsTemplate.convertAndSend("org.example.jms-calculator.response", response, message -> {
            message.setJMSCorrelationID(jmsCorrelationId);
            return message;
        });
    }
}
