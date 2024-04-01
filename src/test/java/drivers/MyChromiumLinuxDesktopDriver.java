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

public class MyChromiumLinuxDesktopDriver extends MyDriverRemote {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromiumLinuxDesktopDriver.class);
    private MainDto mainDto;
    private ChromeDriver chromiumDriver;
    private DevTools devTools;
    private WebDriverManager wdm;

    public MyChromiumLinuxDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new linux chromium browser");
        wdm = WebDriverManager.chromiumdriver();
        wdm.setup();
        try{
            //This initialization of chrome is just a work around from - https://github.com/SeleniumHQ/selenium/issues/7788
            chromiumDriver = new ChromeDriver(chromeOptions());
//            devTools = chromiumDriver.getDevTools();
//            devTools.createSession(); //this for tracing
//            mainDto.setDevTools(devTools);
            this.mainDto.setDriver(chromiumDriver);
        }
        catch (SessionNotCreatedException e){
            LOGGER.debug("SessionNotCreatedException occurred! will retry to create session! ", e);
            chromiumDriver = new ChromeDriver(chromeOptions());
            this.mainDto.setDriver(chromiumDriver);
            LOGGER.debug("new session success!");
        }
    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chromium options");
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        mainDto.setPlatformType(PlatformTypes.UNIX);
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless"))){
            options.addArguments("--headless");
            options.addArguments("disable-infobars","--window-size=1920,1080");
        }
        return options;
    }

}
