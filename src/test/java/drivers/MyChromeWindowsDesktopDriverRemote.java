package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyChromeWindowsDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeWindowsDesktopDriverRemote.class);
    private MainDto mainDto;

    public MyChromeWindowsDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new windows chrome browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, chromeOptions());
        this.mainDto.setDriver(remoteWebDriver);
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chrome options");
        setOption(new ChromeOptions());
        setCapability("name", "ChromeWindowsDesktopRemote");
        setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        setCapabilityUserName();
        setCapabilityAccessKey();
        ChromeOptions co = (ChromeOptions)getOption();
        co.addArguments("--disable-notifications");
//        co.setHeadless(true); //for headless
        mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return co;
    }

}
