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

public class MyChromeMacDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeMacDesktopDriverRemote.class);
    private MainDto mainDto;

    public MyChromeMacDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new chrome mac browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, chromeOptions());
        this.mainDto.setDriver(remoteWebDriver);
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chrome options");
        setOption(new ChromeOptions());
        setCapability("name", "ChromeMacDesktopRemote");
        setCapability("browserVersion","85.0");//only version 85 available for free lambda
        setCapability(CapabilityType.PLATFORM_NAME, Platform.MOJAVE);//many platform avaialble for free, high sierra, sierra, El Capitan Yosimite
        setCapabilityUserName();
        setCapabilityAccessKey();
        ChromeOptions co = (ChromeOptions)getOption();
        co.addArguments("--disable-notifications");

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running chrome on headless mode!");
            co.addArguments("--headless");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
            setOption(co);
        }
        else
            mainDto.setPlatformType(PlatformTypes.MAC);
        return co;
    }
}
