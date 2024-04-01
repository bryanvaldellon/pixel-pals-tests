package drivers;

import com.google.common.collect.ImmutableMap;
import dto.MainDto;
import enums.PlatformTypes;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.util.HashMap;

public class MyChromeAndroidDriverRemote extends MyAndroidDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeAndroidDriverRemote.class);
    private MainDto mainDto;

    public MyChromeAndroidDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new chrome android browser remote");
        launchAndroid();
    }

    private void launchAndroid(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserstack.user", SystemUtils.getSystemProperty("browserstack.user"));
        caps.setCapability("browserstack.key", SystemUtils.getSystemProperty("browserstack.key"));
        Pair<String, String> androidDevice = mainDto.getBrowserstackAndroidDevice();
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("osVersion", androidDevice.getRight());
        browserstackOptions.put("deviceName", androidDevice.getLeft());
        browserstackOptions.put("realMobile", "true");
        browserstackOptions.put("local", "true");
        browserstackOptions.put("projectName", "MyChromeAndroidDriverRemote");
        browserstackOptions.put("buildName", SystemUtils.getSystemProperty("browserstack.build"));
        browserstackOptions.put("sessionName", getBrowserStackTestName(mainDto.getScenario()));
        browserstackOptions.put("localIdentifier", SystemUtils.getEnvironmentVariable("GITHUB_RUN_ID"));
        caps.setCapability("bstack:options", browserstackOptions);
        caps.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        String userName = SystemUtils.getSystemProperty("browserstack.user");
        String accessKey = SystemUtils.getSystemProperty("browserstack.key");
        mainDto.setAppiumDriver(launchAndroidDriverRemote(userName, accessKey, caps));
        mainDto.setPlatformType(PlatformTypes.ANDROID);
    }


}
