package drivers;

import dto.MainDto;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverUtils;
import utils.ExceptionUtils;
import utils.SystemUtils;

@NoArgsConstructor
public class DriverBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverBase.class);

    private MainDto mainDto;
    private String driverType;
    private String appPackage;
    private String appActivity;

    public DriverBase(MainDto mainDto) {
        this.mainDto = mainDto;
        setDriver();
        setDriverType();
        DriverUtils.getRequestsAndResponseUrls(mainDto);
        DriverUtils.getURL(mainDto);
    }

    private void setDriverType(){
        LOGGER.debug("setting up driver..");
        switch (this.mainDto.getDriverType()){
            case "ChromeWindowsDesktop": new MyChromeWindowsDesktopDriver(this.mainDto); break;
            case "ChromeWindowsDesktopRemote": new MyChromeWindowsDesktopDriverRemote(this.mainDto); break;
            case "ChromeAndroid": new MyChromeAndroidDriver(this.mainDto); break;
//            case "ChromeAndroidRemote": new MyChromeAndroidDriverRemote(this.mainDto); break;
            case "FirefoxWindowsDesktopRemote": new MyFirefoxWindowsDesktopDriverRemote(this.mainDto); break;
            case "EdgeWindowsDesktop": new MyEdgeWindowsDesktopDriver(this.mainDto); break;
            case "EdgeWindowsDesktopRemote": new MyEdgeWindowsDesktopDriverRemote(this.mainDto); break;
            case "SafariMacDesktopRemote": new MySafariMacDesktopDriverRemote(this.mainDto); break;
            case "ChromeMacDesktop": new MyChromeMacDesktopDriver(this.mainDto); break;
            case "SafariMacDesktop": new MySafariMacDesktopDriver(this.mainDto); break;
            case "ChromeMacDesktopRemote": new MyChromeMacDesktopDriverRemote(this.mainDto); break;
//            case "SafariIPhoneRemote": new MySafariIPhoneDriverRemote(this.mainDto); break;
            case "OperaWindowsDesktopRemote": new MyOperaWindowsDesktopDriverRemote(this.mainDto); break;
            case "OperaMacDesktopRemote": new MyOperaMacDesktopDriverRemote(this.mainDto); break;
            case "ChromeLinuxDesktop": new MyChromeLinuxDesktopDriver(this.mainDto); break;
            case "FirefoxWindowsDesktop": new MyFirefoxWindowsDesktopDriver(this.mainDto);break;
            case "FirefoxMacDesktop": new MyFirefoxMacDesktopDriver(this.mainDto);break;
            case "FirefoxLinuxDesktop": new MyFirefoxLinuxDesktopDriver(this.mainDto);break;
//            case "AppiumAndroidRemote": new MyAppiumAndroidDriverRemote(this.mainDto, appPackage, appActivity);break;
            case "ChromiumLinuxDesktop": new MyChromiumLinuxDesktopDriver(this.mainDto); break;
            case "EdgeLinuxDesktop": new MyEdgeLinuxDesktopDriver(this.mainDto); break;
            default:
                ExceptionUtils.tryCatchExceptionHandler("invalid driver type! " + this.mainDto.getDriverType());
        }
    }

    private void setDriver(){
        LOGGER.debug("setting driver type..");
        driverType = SystemUtils.getSystemProperty("driver.type");
        this.mainDto.setDriverType(driverType);
        LOGGER.debug("driver.type = {}", driverType);
    }
}
