package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySafariMacDesktopDriver {
	private static final Logger LOGGER = LoggerFactory.getLogger(MySafariMacDesktopDriver.class);
    private MainDto mainDto;

    public MySafariMacDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new safari mac browser");
        this.mainDto.setDriver(new SafariDriver(safariOptions()));
            }

    private SafariOptions safariOptions(){
        LOGGER.debug("setting safari options");
        SafariOptions co = new SafariOptions();
        co.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
            mainDto.setPlatformType(PlatformTypes.MAC);
        return co;
    }

}
