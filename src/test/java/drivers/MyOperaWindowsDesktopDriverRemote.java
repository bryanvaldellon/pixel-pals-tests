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

public class MyOperaWindowsDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyOperaWindowsDesktopDriverRemote.class);
    private MainDto mainDto;

    public MyOperaWindowsDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new windows opera browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, operaOptions());
        this.mainDto.setDriver(remoteWebDriver);
    }

    private ChromeOptions operaOptions(){
        LOGGER.debug("setting opera options");
        setCapability("name", "OperaWindowsDesktopRemote");
        setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--disable-notifications");

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running chrome on headless mode!");
            co.setCapability("headless", "true");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
            setOption(co);
        }
        else
            mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return co;
    }
}
