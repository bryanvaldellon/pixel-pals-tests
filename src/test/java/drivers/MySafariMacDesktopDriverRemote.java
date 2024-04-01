package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MySafariMacDesktopDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySafariMacDesktopDriverRemote.class);
    private MainDto mainDto;

    public MySafariMacDesktopDriverRemote(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new safari mac browser remote");
        setHubURL();
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, safariOptions());
        this.mainDto.setDriver(remoteWebDriver);
    }

    private SafariOptions safariOptions(){
        LOGGER.debug("setting safari options");
        SafariOptions capabilities = new SafariOptions();
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        capabilities.setCapability("user", SystemUtils.getSystemProperty("browserstack.user"));
        capabilities.setCapability("accessKey",SystemUtils.getSystemProperty("browserstack.key"));
        capabilities.setCapability("command-timeout", SystemUtils.getConfig("explicit.wait.limit"));
        capabilities.setCapability("idle-timeout", SystemUtils.getConfig("explicit.wait.limit"));
        capabilities.setCapability("project",  "MySafariMacDesktopDriverRemote");
        capabilities.setCapability("build", SystemUtils.getSystemProperty("browserstack.build"));
        capabilities.setCapability("name", getBrowserStackTestName(mainDto.getScenario()));
        capabilities.setCapability("browserstack.console", "verbose");
        capabilities.setCapability("browserstack.networkLogs", "true");
        capabilities.setCapability("browserstack.disableCorsRestrictions", "true");
        //TODO for now only safari versions 11+ is working for vpn https://fazzfinancial.atlassian.net/browse/STXSDET-446
        List<Pair<String, String>> browsers = Arrays.asList(
//                Pair.of("Monterey", "15.3"),
//                Pair.of("Big Sur", "14.1"),
                Pair.of("Catalina", "13.1"), //TODO this safari version works on linux
//                Pair.of("Mojave", "12.1"),
                Pair.of("High Sierra", "11.1") //TODO this safari version works on linux
        );
        Pair<String, String> randomBrowser = browsers.get(SystemUtils.getRandomInt(browsers.size()));
        capabilities.setCapability("browserVersion", randomBrowser.getRight());
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", "OS X");
        browserstackOptions.put("osVersion", randomBrowser.getLeft());
        browserstackOptions.put("local", "true");//bs local
        browserstackOptions.put("realMobile", "true");
        browserstackOptions.put("localIdentifier", SystemUtils.getEnvironmentVariable("GITHUB_RUN_ID"));
        capabilities.setCapability("bstack:options", browserstackOptions);
//        mainDto.setBrowserstack(true);
        mainDto.setPlatformType(PlatformTypes.MAC);
        return capabilities;
    }

}
