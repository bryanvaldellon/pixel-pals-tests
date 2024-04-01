package stepdefinitions;

import dto.MainDto;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.*;
import io.cucumber.plugin.event.Result;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DateUtils;
import utils.DriverUtils;
import utils.LoggerUtils;
import utils.SystemUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Hooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
    private static String timeStamp;
    private static String reportsName = "test automation cucumber ";
    private MainDto mainDto;

    @AfterStep
    public void afterStep(Scenario scenario){
        if(Boolean.parseBoolean(SystemUtils.getConfig("take.screenshot.after.step")))
            DriverUtils.takeScreenshot(mainDto);
    }

    @BeforeStep
    public void beforeStep(Scenario scenario){
        if(Boolean.parseBoolean(SystemUtils.getConfig("take.screenshot.before.step")))
            DriverUtils.takeScreenshot(mainDto);
    }

    @Before
    public void beforeScenario(Scenario scenario){
        LOGGER.debug("starting scenario = {}", scenario.getName());
        LoggerUtils.setScenarioName(scenario);
        this.mainDto.setScenario(scenario);
    }


    @BeforeAll
    public static void beforeAll(){
        LOGGER.debug("before all tests");
        SystemUtils.setSystemProperty("dataproviderthreadcount", SystemUtils.getSystemProperty("dataproviderthreadcount"));
    }

    @After
    public void afterScenario(Scenario scenario){
        LOGGER.debug("executing after scenario hooks!");
        SystemUtils.createTextFile("target/failedScenarios.txt");
        LOGGER.debug("Scenario status = {}", scenario.getStatus());
        DriverUtils.takeScreenshot(mainDto);
        mainDto.teardownDriver();
    }

    private String getErrorMessage(Scenario scenario) {
        Field f;
        String error = "No error provided! please check cucumber report!";
        try {
            //this is how you get private values
            f = scenario.getClass().getDeclaredField("delegate");
            f.setAccessible(true);
            TestCaseState testCaseState = (TestCaseState) f.get(scenario);
            f = testCaseState.getClass().getDeclaredField("stepResults");
            f.setAccessible(true);
            List<Result> results = (ArrayList) f.get(testCaseState);
            for(Result result:results){
                LOGGER.debug("result = {}", result.getStatus());
                if(result.getStatus().toString().equals("FAILED")){
                    error = result.getError().getMessage();
                    break;
                }
            }
            if(error == null){
                return "No error provided! please check cucumber report!";
            }
            else if(error.contains("An element could not be located on the page")){
                LOGGER.debug(error);
                error = "no such element found on error message! please check the logs for detailed error!";
                LOGGER.debug(error);
            }
        }
        catch ( NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            e.printStackTrace();
        }

        return error;
    }

    private String getReasonFromScenario(Scenario scenario) {
        String errorMessage = getErrorMessage(scenario);
        errorMessage = errorMessage.replace("\n","");
        errorMessage = errorMessage.replace("\"", "");
        return  "\"" +  errorMessage +"\"";
    }

    private String generateScenarioTags(Scenario scenario) {
        LOGGER.debug("tag names = {}" , scenario.getSourceTagNames().toString());
        String scenarioTag = "(";
        int ctr = 0;
        for(String tagName : scenario.getSourceTagNames()){
            scenarioTag = scenarioTag + tagName;
            if(ctr < scenario.getSourceTagNames().size() - 1)
                scenarioTag = scenarioTag + " and ";
            ctr++;
        }
        scenarioTag = scenarioTag + ")";
        LOGGER.debug("scenarioTag = {}", scenarioTag);
        return scenarioTag;
    }


}
