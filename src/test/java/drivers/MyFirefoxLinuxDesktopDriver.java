package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.io.File;

public class MyFirefoxLinuxDesktopDriver extends MyDriverRemote {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFirefoxLinuxDesktopDriver.class);
    private MainDto mainDto;
    private DevTools devTools;
    private WebDriverManager wdm;

    public MyFirefoxLinuxDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new linux firefox browser");
        wdm = WebDriverManager.firefoxdriver();
        wdm.setup();
        FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOption());
        this.mainDto.setDriver(firefoxDriver);
//        System.setProperty("webdriver.firefox.whitelistedIps", ""); //for docker runs
//        devTools = firefoxDriver.getDevTools();
//        devTools.createSession(); //this for tracing
//        mainDto.setDevTools(devTools);
    }

    private FirefoxOptions firefoxOption(){
        LOGGER.debug("setting firefox options");

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir",System.getProperty("user.dir") + File.separator + "downloads");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/zip");

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
        options.setCapability("acceptInsecureCerts",true);
        String driverPath = wdm.getDownloadedDriverPath();
//        driverPath = driverPath + SystemUtils.getFileNameFromDir("geckodriver", driverPath);
//        LOGGER.debug("driverPath = {}", driverPath);
//        options.setBinary(driverPath);
        System.setProperty("webdriver.gecko.driver", driverPath);
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless"))){
            options.addArguments("--headless");
        }

        mainDto.setPlatformType(PlatformTypes.UNIX);
//        setOption(options);
        return options;
    }
}
