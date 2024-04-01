package steps;

import drivers.DriverBase;
import dto.MainDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LandingPage;
import utils.*;

@AllArgsConstructor
@NoArgsConstructor
public class LandingStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandingStep.class);
    private MainDto mainDto;
    LandingPage landingPage;

    public void setApplicationLanding(String appName) {
        mainDto.setApp(appName);
        new DriverBase(mainDto);
    }

    public void validatePixelPalAppIsDisplayed() {
        WaitUtils.waitUntilElementIsDisplayed(mainDto, landingPage.getApp());
        DriverUtils.takeScreenshot(mainDto);
        AssertionUtils.assertIsDisplayed(mainDto, landingPage.getApp(), "App is not launched!");
    }
}
