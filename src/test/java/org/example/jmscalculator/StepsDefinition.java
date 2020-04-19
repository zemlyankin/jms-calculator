package org.example.jmscalculator;

import com.google.common.primitives.Ints;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.example.jmscalculator.request.Operation;
import org.example.jmscalculator.testtools.Receiver;
import org.example.jmscalculator.testtools.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
        (classes=JmsCalculatorApplication.class)
public class StepsDefinition {

    @Autowired
    protected Sender sender;

    @Autowired
    protected Receiver receiver;

    private Operation operation;
    private List<Integer> operandList;
    private int result;

    @When("^operation (\\w+)$")
    public void get_operation_add(String operator) {
            this.operation = Operation.valueOf(operator);
    }

    @When("^two operands are (-?\\w+) and (-?\\w+)$")
    public void two_operands_are_and(String arg1, String arg2) {
        this.operandList = Ints.asList(strToInt(arg1), strToInt(arg2));
    }

    @When("^send message$")
    public void send_message() {
        sender.send(operation, operandList);
    }

    @When("^get result$")
    public void get_message() throws InterruptedException {
        Thread.sleep(1000);
        result = receiver.getResult();
    }

    @Then("^result = (-?\\w+)$")
    public void getting_sum(String arg1) {
        assertThat(result).isEqualTo(strToInt(arg1));
    }
    public Integer strToInt (String str) {
        Integer result;
        switch (str) {
            case "null":
                result = null;
                break;
            case "MAX_VALUE":
                result = Integer.MAX_VALUE;
                break;
            case "MIN_VALUE":
                result = Integer.MIN_VALUE;
                break;
            default:
                result = Integer.parseInt(str);
        }
        return result;
    }

}
