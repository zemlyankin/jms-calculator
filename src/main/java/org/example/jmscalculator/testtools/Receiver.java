package org.example.jmscalculator.testtools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jmscalculator.response.CalculatorResponse;
import org.example.jmscalculator.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component("receiver")
public class Receiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    private Integer result;

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "org.example.jms-calculator.response")
    public void receive(String message) {
        LOGGER.info("Receiver received message='{}'", message);
        try {
//            jmsTemplate.setReceiveTimeout(100_000);
            CalculatorResponse calculatorResponse = objectMapper.readValue(message, CalculatorResponse.class);
            result = calculatorResponse.getResult();
            latch.countDown();
        } catch (JsonProcessingException e) {
            try {
                ErrorResponse errorResponse = objectMapper.readValue(message,ErrorResponse.class);
                System.out.println("Exception occurred: " + errorResponse.getError());
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Integer getResult() {
        return result;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
