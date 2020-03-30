package org.example.jmscalculator.testtools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.jmscalculator.request.CalculatorRequest;
import org.example.jmscalculator.request.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Sender {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public void send(Operation operation, List<Integer> operands) {
        CalculatorRequest message = new CalculatorRequest();
        message.setOperator(operation);
        message.setOperands(operands);

//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        jmsTemplate.setMessageConverter(converter);
        jmsTemplate.convertAndSend("org.example.jms-calculator.request", message);
        LOGGER.info("sending message='{}'", message);
    }
}
