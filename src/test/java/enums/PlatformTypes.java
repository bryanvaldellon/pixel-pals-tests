package enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PlatformTypes {

    WINDOWS(DeviceType.DESKTOP),
    IOS(DeviceType.MOBILE),
    MAC(DeviceType.DESKTOP),
    ANDROID(DeviceType.MOBILE),
    UNIX(DeviceType.DESKTOP),
    HEADLESS(DeviceType.DESKTOP);

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformTypes.class);
    private final DeviceType deviceType;

    PlatformTypes(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public DeviceType getDeviceType(){
        LOGGER.debug("getting device type = {}", deviceType);
        return deviceType;
    }
}
