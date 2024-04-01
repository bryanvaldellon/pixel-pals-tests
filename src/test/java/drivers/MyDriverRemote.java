package drivers;

import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class MyDriverRemote extends MyDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDriverRemote.class);

    protected URL hubUrl;

    public URL getHubUrl() {
        LOGGER.debug("getting hub URL = {}", hubUrl);
        return hubUrl;
    }

    protected void setHubURL(){
        try {
//            hubUrl = new URL(SystemUtils.getConfig("hub.url")); //dont delete this, some services is able to use this capab
            hubUrl = new URL("https://" +
                    SystemUtils.getSystemProperty("browserstack.user") +
                    ":" +
                    SystemUtils.getSystemProperty("browserstack.key") +
                    SystemUtils.getConfig("hub.url"));
        } catch (MalformedURLException e) {
            LOGGER.debug("MalformedURLException - {}", e.getMessage());
        }
        LOGGER.debug("hubUrl = {}", hubUrl);
    }

    protected void setHubURL(String hubURL){
        try {
//            hubUrl = new URL(SystemUtils.getConfig("hub.url")); //dont delete this, some services is able to use this capab
            this.hubUrl = new URL("https://" +
                    SystemUtils.getSystemProperty("browserstack.user") +
                    ":" +
                    SystemUtils.getSystemProperty("browserstack.key") +
                    hubURL);
        } catch (MalformedURLException e) {
            LOGGER.debug("MalformedURLException - {}", e.getMessage());
        }
        LOGGER.debug("hubUrl = {}", this.hubUrl);
    }

    protected String getBrowserStackTestName(Scenario scenario) {
        String[] parts = scenario.getUri().toString().split("src/test/resources/features/");
        String featureFileNameDotFeature = parts[1]; //"login.feature"
        parts = featureFileNameDotFeature.split(".feature");
        String featureFileName = parts[0];
        return "[" + featureFileName + "] " + scenario.getName();
    }
}
