package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyEdgeLinuxDesktopDriver {
    private WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyEdgeLinuxDesktopDriver.class);
    private MainDto mainDto;
    private DevTools devTools;
    private EdgeDriver edgeDriver;
    private WebDriverManager wdm;

    public MyEdgeLinuxDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new linux edge browser");
        wdm = WebDriverManager.edgedriver();
        wdm.setup();
        EdgeDriver edgeDriver = null;
        try{
            edgeDriver = new EdgeDriver(edgeOptions());
            this.mainDto.setDriver(edgeDriver);
            devTools = edgeDriver.getDevTools();
            devTools.createSession(); //this for tracing
            mainDto.setDevTools(devTools);
        }
        catch (SessionNotCreatedException e){
            LOGGER.debug("SessionNotCreatedException occurred! will retry to create session!", e);
            edgeDriver = new EdgeDriver(edgeOptions());
            this.mainDto.setDriver(edgeDriver);
            LOGGER.debug("new session success!");
        }
    }

    private EdgeOptions edgeOptions(){
        LOGGER.debug("setting edge options");
        EdgeOptions edgeOptions = new EdgeOptions();
//        edgeOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.UNIX);
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless"))){
            edgeOptions.addArguments("--window-size=1920,1080");
            edgeOptions.addArguments("--headless");
            edgeOptions.setExperimentalOption("detach", true);
        }
        String driverPath = wdm.getDownloadedDriverPath();
//        driverPath = driverPath + SystemUtils.getFileNameFromDir("geckodriver", driverPath);
//        LOGGER.debug("driverPath = {}", driverPath);
//        options.setBinary(driverPath);
        System.setProperty("webdriver.edge.driver", driverPath);
        edgeOptions.addArguments("--disable-dev-shm-usage");
        mainDto.setPlatformType(PlatformTypes.UNIX);
        return edgeOptions;
    }
}
