package utils;

import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CucumberUtils.class);

    /**
     * Created to hide the public constructor of this class
     */
    private CucumberUtils() {
    }

    public static void attachStringToScenario(Scenario scenario, Object s, String addText){
        if(s==null){
            LOGGER.debug("string is null!");
        }
        else {
            LOGGER.debug("attaching to scenario = {}", scenario);
            LOGGER.debug("attaching the string = {}", s);
            scenario.log(addText + " " + s);
        }

    }


}
