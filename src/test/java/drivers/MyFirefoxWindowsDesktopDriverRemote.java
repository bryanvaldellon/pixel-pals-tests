package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFirefoxWindowsDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFirefoxWindowsDesktopDriverRemote.class);
    private MainDto mainDto;

    public MyFirefoxWindowsDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new firefox browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, firefoxOptions());
        this.mainDto.setDriver(remoteWebDriver);
    }

    private FirefoxOptions firefoxOptions(){
        LOGGER.debug("setting firefox options");
        setOption(new FirefoxOptions());
        setCapability("name", "FirefoxWindowsDesktopRemote");
        setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        setCapabilityUserName();
        setCapabilityAccessKey();
        FirefoxOptions co = (FirefoxOptions) getOption();
        co.addArguments("--disable-notifications");

        mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return co;
    }
}
