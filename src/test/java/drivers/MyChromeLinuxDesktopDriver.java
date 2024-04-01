package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.io.File;
import java.util.HashMap;

public class MyChromeLinuxDesktopDriver extends MyDriverRemote {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeLinuxDesktopDriver.class);
    private MainDto mainDto;
    private ChromeDriver chromeDriver;
    private DevTools devTools;
    private WebDriverManager wdm;

    public MyChromeLinuxDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new linux chrome browser");
        wdm = WebDriverManager.chromedriver();
        wdm.setup();
        try{
            chromeDriver = new ChromeDriver(chromeOptions());
            this.mainDto.setDriver(chromeDriver);
        }
        catch (SessionNotCreatedException e){
            LOGGER.debug("SessionNotCreatedException occurred! will retry to create session!");
            chromeDriver = new ChromeDriver(chromeOptions());
            this.mainDto.setDriver(chromeDriver);
            LOGGER.debug("new session success!");
        }
//        System.setProperty("webdriver.chrome.silentOutput", "true");
//        System.setProperty("webdriver.chrome.whitelistedIps", ""); //for docker runs
//        devTools = chromeDriver.getDevTools();
//        devTools.createSession(); //this for tracing
//        mainDto.setDevTools(devTools);

    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chrome options");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); //for chrome not to detect automation
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless"))) {
            options.addArguments("--headless");
        }
        options.addArguments("--disable-dev-shm-usage");
        options.setExperimentalOption("detach", true);
        options.addArguments("disable-infobars","--window-size=1920,1080");
        options.setAcceptInsecureCerts(true);
        // set download path
        String downloadFilepath = System.getProperty("user.dir") + File.separator + "downloads";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        options.setExperimentalOption("prefs", chromePrefs);
        mainDto.setPlatformType(PlatformTypes.UNIX);
        setOption(options);
        return options;
    }
}
