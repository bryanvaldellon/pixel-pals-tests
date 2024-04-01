package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyEdgeWindowsDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyEdgeWindowsDesktopDriverRemote.class);
    private MainDto mainDto;

    public MyEdgeWindowsDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new edge browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, edgeOptions());
        this.mainDto.setDriver(remoteWebDriver);
    }

    private EdgeOptions edgeOptions(){
        LOGGER.debug("setting edge options");
        setOption(new EdgeOptions());
        setCapability("name", "EdgeWindowsDesktopRemote");
        setCapability("browserName", "MicrosoftEdge");
//        setCapability("browserVersion","85.0");//only version 85 available on lambda free
        setCapabilityUserName();
        setCapabilityAccessKey();
        EdgeOptions ca = (EdgeOptions)getOption();

        List<Pair<String, String>> browsers = Arrays.asList(
                Pair.of("11", "104"),
                Pair.of("11", "105"),
                Pair.of("11", "103"),
                Pair.of("11", "100"),
                Pair.of("10", "97"),
                Pair.of("10", "90")
        );

        Pair<String, String> randomBrowser = browsers.get(SystemUtils.getRandomInt(browsers.size()));
        setCapability("browserVersion", randomBrowser.getRight());
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", randomBrowser.getLeft());
        browserstackOptions.put("local", "true");//bs local
        browserstackOptions.put("realMobile", "true");
        setCapability("bstack:options", browserstackOptions);
//        mainDto.setBrowserstack(true);
        mainDto.setPlatformType(PlatformTypes.WINDOWS);

        if(Boolean.parseBoolean(SystemUtils.getSystemProperty("headless"))){
            LOGGER.debug("running edge on headless mode!");
            ca.setCapability("headless","true");
            mainDto.setPlatformType(PlatformTypes.HEADLESS);
            setOption(ca);
        }
        else
            mainDto.setPlatformType(PlatformTypes.WINDOWS);
        return ca;
    }
}
