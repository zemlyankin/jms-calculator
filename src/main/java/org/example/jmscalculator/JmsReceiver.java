package org.example.jmscalculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jmscalculator.request.CalculatorRequest;
import org.example.jmscalculator.response.CalculatorResponse;
import org.example.jmscalculator.response.ErrorResponse;
import org.example.jmscalculator.service.CalculatorService;
import org.example.jmscalculator.testtools.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(JmsReceiver.class);

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "org.example.jms-calculator.request")
    public void receiveMessage(String message) {
        Object response;
        LOGGER.info("JmsReceiver received message = '{}'", message);
        try {
            CalculatorRequest calculatorRequest = objectMapper.readValue(message, CalculatorRequest.class);
            response = calculatorService.calculate(calculatorRequest);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            response = new ErrorResponse(e.getMessage());
        }
        jmsTemplate.convertAndSend("org.example.jms-calculator.response", response);
        LOGGER.info("JmsReceiver send message = '{}'", response);
    }
}
