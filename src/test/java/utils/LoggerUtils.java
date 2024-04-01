package utils;

import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LoggerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

    public static void setScenarioName(Scenario scenario){
        LOGGER.debug("setting scenario name for logger");
        MDC.put("scenarioName", scenario.getName().replace(" ",""));
    }

}
