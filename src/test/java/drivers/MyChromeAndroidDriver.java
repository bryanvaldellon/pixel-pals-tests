package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyChromeAndroidDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeAndroidDriver.class);

    private MainDto mainDto;

    public MyChromeAndroidDriver(MainDto mainDto) {
        LOGGER.debug("creating new android chrome browser local");
        this.mainDto = mainDto;
        this.mainDto.setDriver(new AndroidDriver(chromeOptions()));
        mainDto.setPlatformType(PlatformTypes.ANDROID);
    }

    private ChromeOptions chromeOptions(){
        ChromeOptions co = new ChromeOptions();
//        co.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, "android");
        co.setCapability("androidPackage", "com.android.chrome");
//        co.setCapability("chromedriverExecutable", Utils.getConfig("chrome.driver.path") + "chromedriver.exe");
//        co.setExperimentalOption("androidPackage", "com.android.chrome");
        co.setExperimentalOption("w3c", false);//turn off w3c mode for new chromedriver
        return co;
    }



    private void setChromeDriverpath(){
        LOGGER.debug("setting chrome path!");
        System.setProperty("webdriver.chrome.driver", SystemUtils.getConfig("chrome.driver.path") + "chromedriver.exe");
    }
}
