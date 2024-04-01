package stepdefinitions;

import dto.MainDto;
import io.cucumber.java.en.Given;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.EnvironmentStep;

@AllArgsConstructor
public class EnvironmentStepDefinition {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentStepDefinition.class);

    private MainDto mainDto;
    private EnvironmentStep environmentStep;

    @Given("^.* on test environment$")
    public void setEnvironment() {
        environmentStep.setEnvironment();
    }
    @Given("^.* on \"(.*)\" test environment$")
    public void setEnvironment(String environment) {
        environmentStep.setEnvironment(environment);
    }
}
