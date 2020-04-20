package org.example.jmscalculator;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
@SpringBootTest
public class JmsCalculatorApplicationTests {


}
