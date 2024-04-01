package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyFirefoxMacDesktopDriver extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFirefoxMacDesktopDriver.class);
    private MainDto mainDto;

    public MyFirefoxMacDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new firefox mac browser local");
        this.mainDto.setDriver(new FirefoxDriver(firefoxOptions()));
    }

    private FirefoxOptions firefoxOptions(){
        LOGGER.debug("setting firefox options");
        setOption(new FirefoxOptions());
        setCapability("name", "FirefoxMacDesktop");
        setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        FirefoxOptions co = (FirefoxOptions) getOption();

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running firefox on headless mode!");
            co.addArguments("--headless");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
            setOption(co);
        }
        else
            mainDto.setPlatformType(PlatformTypes.MAC);
        return co;
    }
}
