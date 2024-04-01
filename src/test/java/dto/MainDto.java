package dto;
import enums.Apps;
import enums.PlatformTypes;
import io.cucumber.java.Scenario;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.devtools.DevTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

@Data
public class MainDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainDto.class);
    private Apps app;
    private WebDriver driver;
    private String driverType;
    private String envURL;
    private String testEnv;
    private Scenario scenario;
    private boolean isPerformanceTest;
    private PlatformTypes platformType;
    private DevTools devTools;

    public MainDto(){

    }

    public Scenario getScenario() {
        LOGGER.debug("getting scenario = {}", scenario.getName());
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        LOGGER.debug("setting scenario = {}", scenario.getName());
        this.scenario = scenario;
    }

    public String getTestEnv() {
        LOGGER.debug("getting test env = {}", testEnv);
        return testEnv;
    }

    public void setTestEnv(String testEnv) {
        LOGGER.debug("setting test env = {}", testEnv);
        this.testEnv = testEnv;
    }

    public PlatformTypes getPlatformType() {
        LOGGER.debug("getting platform type = {}", platformType);
        return platformType;
    }


    public String getEnvURL() {
        LOGGER.debug("getting envURl");
        LOGGER.debug("testEnv = ", testEnv);
        switch (testEnv){
            case "dev": envURL = app.getDev(); break;
            case "test": envURL = app.getTest(); break;
            case "staging": envURL = app.getStaging(); break;
            case "prod": envURL = app.getProd(); break;
        }
        LOGGER.debug("envURL = {}", envURL);
        return envURL;
    }

    public void setEnvURL(String envURL) {
        this.envURL = envURL;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        LOGGER.debug("setting driver type = {}", driverType);
        this.driverType = driverType;
    }

    public Apps getApp() {
        LOGGER.debug("getting app = {}", app);
        return app;
    }
    public void setApp(String app){
        LOGGER.debug("setting app");
        this.app = Apps.valueOf(app);
        LOGGER.debug("setting app = {}", this.app);
    }

    public WebDriver getDriver() {
        LOGGER.debug("getting driver = {}", driver);
        return driver;
    }

    public void setDriver(WebDriver driver) {
        LOGGER.debug("setting driver = {}", driver);
        this.driver = driver;
    }

    public void teardownDriver() {
        LOGGER.debug("quitting driver = {}", driver);
        try{
            if (driver != null) {
                if(Boolean.parseBoolean(SystemUtils.getConfig("isclose.browser.after.execution"))){
                    driver.quit();
                    LOGGER.debug("checking if driver is still existing = {}", driver);
                }
            }
        }
        catch (WebDriverException e){
            LOGGER.debug("retrying to close driver!");
            driver.quit();
        }

    }



}
