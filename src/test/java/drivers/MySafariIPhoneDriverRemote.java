package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.util.HashMap;

public class MySafariIPhoneDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySafariIPhoneDriverRemote.class);
    private MainDto mainDto;

    public MySafariIPhoneDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new safari iphone browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new IOSDriver(hubUrl, safariOptions());
        this.mainDto.setDriver(remoteWebDriver);
    }

    private SafariOptions safariOptions(){
        LOGGER.debug("setting safari options");
        SafariOptions caps = new SafariOptions();
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserstack.user", SystemUtils.getSystemProperty("browserstack.user"));
        caps.setCapability("browserstack.key", SystemUtils.getSystemProperty("browserstack.key"));
        Pair<String, String> iosDevice = mainDto.getBrowserstackIosDevice();
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("osVersion", iosDevice.getRight());
        browserstackOptions.put("deviceName", iosDevice.getLeft());
        browserstackOptions.put("realMobile", "true");
        browserstackOptions.put("local", "true");
        browserstackOptions.put("projectName", "MySafariIPhoneDriverRemote");
        browserstackOptions.put("buildName", SystemUtils.getSystemProperty("browserstack.build"));
        browserstackOptions.put("sessionName", getBrowserStackTestName(mainDto.getScenario()));
        browserstackOptions.put("localIdentifier", SystemUtils.getEnvironmentVariable("GITHUB_RUN_ID"));
        caps.setCapability("bstack:options", browserstackOptions);
        mainDto.setPlatformType(PlatformTypes.IOS);
        return caps;
    }
}
