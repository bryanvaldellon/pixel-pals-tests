package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyFirefoxWindowsDesktopDriver extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFirefoxWindowsDesktopDriver.class);
    private MainDto mainDto;
    private WebDriverManager wdm;

    public MyFirefoxWindowsDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new firefox browser local");
        wdm = WebDriverManager.firefoxdriver();
        wdm.setup();
        this.mainDto.setDriver(new FirefoxDriver(firefoxOptions()));
    }

    private FirefoxOptions firefoxOptions(){
        LOGGER.debug("setting firefox options");
        setOption(new FirefoxOptions());
        setCapability("name", "FirefoxWindowsDesktop");
        setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        FirefoxOptions co = new FirefoxOptions();
        String driverPath = wdm.getDownloadedDriverPath();
//        driverPath = driverPath + SystemUtils.getFileNameFromDir("geckodriver", driverPath);
//        LOGGER.debug("driverPath = {}", driverPath);
//        co.setBinary(driverPath);
        System.setProperty("webdriver.gecko.driver", driverPath);

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running firefox on headless mode!");
            co.addArguments("--headless");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
            setOption(co);
        }
        else
            mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return co;
    }
}
