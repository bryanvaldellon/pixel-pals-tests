package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyChromeWindowsDesktopDriver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeWindowsDesktopDriver.class);
    private MainDto mainDto;

    public MyChromeWindowsDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new windows chrome browser");
        WebDriverManager.chromedriver().setup();
        this.mainDto.setDriver(new ChromeDriver(chromeOptions()));
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chrome options");
        ChromeOptions co = new ChromeOptions();
        co.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        co.addArguments("--disable-notifications");
//        driverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
//        LOGGER.debug("driverPath = {}", driverPath);
//        co.setBinary(driverPath);
//        co.addArguments("--disable-dev-shm-usage");
//        co.addArguments("--ignore-ssl-errors=yes");
//        co.addArguments("--ignore-certificate-errors");
//        co.addArguments("--no-sandbox");
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless")))
            co.addArguments("--headless");
        //argurment below is the trick to mock as a mobile browser for uploading selfie
        co.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1");
        mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return co;
    }
}
