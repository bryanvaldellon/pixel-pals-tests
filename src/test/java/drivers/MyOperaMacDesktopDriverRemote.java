package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyOperaMacDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyOperaMacDesktopDriverRemote.class);
    private MainDto mainDto;

    public MyOperaMacDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new mac opera browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, operaOptions());
        this.mainDto.setDriver(remoteWebDriver);
    }

    private ChromeOptions operaOptions(){
        LOGGER.debug("setting opera options");
        setOption(new ChromeOptions());
        setCapability("main", "OperaMacDesktopRemote");
        setCapability(CapabilityType.PLATFORM_NAME, Platform.MOJAVE);
        setCapability("browserVersion","70");
        setCapabilityUserName();
        setCapabilityAccessKey();
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--disable-notifications");

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running chrome on headless mode!");
            co.setCapability("headless", "true");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
            setOption(co);
        }
        else
            mainDto.setPlatformType(PlatformTypes.MAC);
        return co;
    }
}
