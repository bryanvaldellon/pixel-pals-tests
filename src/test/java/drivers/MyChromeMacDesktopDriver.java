package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyChromeMacDesktopDriver extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeMacDesktopDriver.class);
    private MainDto mainDto;

    public MyChromeMacDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new chrome mac browser remote");
        this.mainDto.setDriver(new ChromeDriver(chromeOptions()));
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chrome options");
        ChromeOptions co = new ChromeOptions();
        co.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        co.addArguments("--disable-notifications");

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running chrome on headless mode!");
            co.addArguments("--headless");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
        }
        else
            mainDto.setPlatformType(PlatformTypes.MAC);
        return co;
    }
}
