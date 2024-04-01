package stepdefinitions;

import dto.MainDto;
import io.cucumber.java.en.Given;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import steps.LandingStep;

@NoArgsConstructor
@AllArgsConstructor
public class LandingStepDefinition {

    private MainDto mainDto;
    private LandingStep landingStep;

    @Given("^.* on \"(.*)\" application$")
    public void setApplicationLanding(String appName){
        landingStep.setApplicationLanding(appName);

    }
}
