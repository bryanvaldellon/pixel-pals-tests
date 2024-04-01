package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyEdgeWindowsDesktopDriver {
    private WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyEdgeWindowsDesktopDriver.class);
    private MainDto mainDto;
    private DevTools devTools;
    private EdgeDriver edgeDriver;

    public MyEdgeWindowsDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new windows edge browser");
        EdgeDriver edgeDriver = null;
        try{
            edgeDriver = new EdgeDriver(edgeOptions());
            this.mainDto.setDriver(edgeDriver);
            devTools = edgeDriver.getDevTools();
            devTools.createSession(); //this for tracing TODO dont remove this. we'll only apply this when tracing is needed
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
        edgeOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless"))){
            edgeOptions.addArguments("--headless");
        }
        //argurment below is the trick to mock as a mobile browser for uploading selfie
        edgeOptions.addArguments("--window-size=1920,1080");
        mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return edgeOptions;
    }
}
