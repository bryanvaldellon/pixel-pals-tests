package steps;

import drivers.DriverBase;
import dto.MainDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@NoArgsConstructor
public class LandingStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandingStep.class);
    private MainDto mainDto;

    public void setApplicationLanding(String appName) {
        mainDto.setApp(appName);
        new DriverBase(mainDto);
    }
}
