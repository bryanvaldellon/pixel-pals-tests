package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverUtils;
import utils.ExceptionUtils;
import utils.SystemUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MyAppiumAndroidDriverRemote extends MyAndroidDriverRemote{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyAppiumAndroidDriverRemote.class);
    private final MainDto mainDto;
    private final String appPackage;
    private final String appActivity;
    private DesiredCapabilities caps;
    private String device;
    private String osVersion;

    public MyAppiumAndroidDriverRemote(MainDto mainDto, String appPackage, String appActivity) {
        this.mainDto = mainDto;
        this.appPackage = appPackage;
        this.appActivity = appActivity;
        LOGGER.debug("creating new appium driver");
        initializeAndroidApp();
    }

    public void initializeAndroidApp() {
        caps = new DesiredCapabilities();
        caps.setCapability("autoDismissAlerts", true);
        try{
            if (DriverUtils.isRemoteDriver(mainDto)
                    && mainDto.getAppiumDriver() == null
                    && SystemUtils.getConfig("appium.is.local.device").equals("false")) {
                //this is for pipelines that will build most recent apk
                if(Boolean.parseBoolean(SystemUtils.getSystemProperty("browserstack.app.is.upload"))){
                    caps.setCapability("app", SystemUtils.getSystemProperty("browserstack.app"));
                }
                else{ //if you will use a bs:// link
                    SystemUtils.setSystemProperty("browserstack.app", SystemUtils.getConfig("browserstack.apk"));
                    caps.setCapability("app", SystemUtils.getConfig("browserstack.apk"));
                }
                HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
                Pair<String,String> androidDevice = mainDto.getBrowserstackAndroidDevice();
                device = androidDevice.getLeft();
                osVersion = androidDevice.getRight();
                browserstackOptions.put("local", "true");
                browserstackOptions.put("networkLogs", "true");
                browserstackOptions.put("osVersion", osVersion);
                browserstackOptions.put("deviceName", device);
                browserstackOptions.put("localIdentifier", SystemUtils.getEnvironmentVariable("GITHUB_RUN_ID"));
                caps.setCapability("bstack:options", browserstackOptions);//this is for the bs local
                LOGGER.debug("random android device = {} os = {}", device, osVersion);
                caps.setCapability("appPackage", this.appPackage);
                caps.setCapability("appActivity", this.appActivity);
                caps.setCapability("os_version", osVersion);
                caps.setCapability("os", "android");
                caps.setCapability("deviceName", device);
                caps.setCapability("real_mobile", "true");
                caps.setCapability("browserstack.user", SystemUtils.getSystemProperty("browserstack.user"));
                caps.setCapability("browserstack.key", SystemUtils.getSystemProperty("browserstack.key"));
                caps.setCapability("project", SystemUtils.getSystemProperty("browserstack.project"));
                caps.setCapability("build", SystemUtils.getSystemProperty("browserstack.build"));
                caps.setCapability("name", getBrowserStackTestName(mainDto.getScenario()));
                caps.setCapability("fullReset", true);
                caps.setCapability("autoGrantPermissions", true);
//                caps.setCapability("unicodeKeyboard", true);
//                caps.setCapability("resetKeyboard", true);
                String userName = SystemUtils.getSystemProperty("browserstack.user");
                String accessKey = SystemUtils.getSystemProperty("browserstack.key");
                mainDto.setAppiumDriver(launchAndroidDriverRemote(userName, accessKey, caps));
                mainDto.getScenario().attach(
                        mainDto.getAppiumDriver().getSessionId().toString(),
                        "text/plain",
                        "browserstack.session.id"
                );
            }
            else { //this is for local
                caps.setCapability("platformName", "Android");
                caps.setCapability("appPackage", this.appPackage);
                caps.setCapability("appActivity", this.appActivity);
                mainDto.setAppiumDriver(new AndroidDriver(new URL(SystemUtils.getConfig("appium.local.url.default")), caps));
            }
            mainDto.setPlatformType(PlatformTypes.ANDROID);
        }
        catch (MalformedURLException e){
            ExceptionUtils.tryCatchExceptionHandler("MalformedURLException occured!", e);
        }

        DriverUtils.setImplicitWait(mainDto, 10);
        // By default, UIAutomator2 tries to prevent flaky test by waiting for up to 10 seconds for the UI to be idle
        // (all animation finishes). This has made the validation steps where an animation is displayed (e.g., tooltip
        // in home page) slow. Optimizing it by reducing it to 500 ms (same as long animation time for Android,
        // https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/core/res/res/values/config.xml#147).
        // See also: https://developer.android.com/reference/androidx/test/uiautomator/Configurator#setwaitforidletimeout
        DriverUtils.setAppiumSetting(mainDto, Setting.WAIT_FOR_IDLE_TIMEOUT.toString(), 500);
    }

}
