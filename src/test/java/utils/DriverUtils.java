package utils;

import com.google.common.collect.ImmutableMap;
import dto.MainDto;
import enums.DeviceType;
import enums.PlatformTypes;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v118.network.Network;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class DriverUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverUtils.class);
    private static final int EXPLICIT_WAIT_TIME = Integer.parseInt(SystemUtils.getConfig("explicit.wait.limit"));
    private static final int AJAX_WAIT_TIME = Integer.parseInt(SystemUtils.getConfig("ajax.wait.limit"));

    private static WebDriverWait webDriverWait;

    public static void getURL(MainDto mainDto) {
        getURL(mainDto, mainDto.getEnvURL());
    }


    public static void getURL(MainDto mainDto, String url) {
        try{
            LOGGER.debug("launching url = {}", url);
            mainDto.getDriver().get(url);
            maximizeBrowser(mainDto);
        }
        catch (Exception e){
            LOGGER.debug("general error occurred when launching url, will try to re launch!");
            LOGGER.debug("launchURL - ", e);
            SystemUtils.threadSleep(5000);
            mainDto.getDriver().get(url);
            maximizeBrowser(mainDto);
        }
    }

    private static void checkBrowserstackLocal(MainDto mainDto) {
        mainDto.getDriver().get("http://bs-local.com:45691/check");
        boolean isBrowserstackLocalActive = ElementUtils.isElementDisplayed(mainDto, By.xpath("//*[text()=\"Up and running\"]"));
        DriverUtils.takeScreenshot(mainDto);
        LOGGER.debug("isBrowserstackLocalActive = {}", isBrowserstackLocalActive);
    }

    public static void maximizeBrowser(MainDto mainDto) {
        if (Boolean.parseBoolean(SystemUtils.getConfig("maximize.browser"))) {
            LOGGER.debug("maximizing browser!");
            mainDto.getDriver().manage().window().maximize();
        }
    }

    public static void setWindowSize(MainDto mainDto, int width, int height){
        LOGGER.debug("setting window size to width = {} and height = {}", width, height);
        mainDto.getDriver().manage().window().setSize(new Dimension(width, height));
    }

    public static Dimension getWindowSize(MainDto mainDto){
        LOGGER.debug("getting window size");
        Dimension windowSize = mainDto.getDriver().manage().window().getSize();
        LOGGER.debug("windowSize = {}", windowSize);
        return windowSize;
    }

    public static String getCurrentUrl(MainDto mainDto){
        LOGGER.debug("getting current url");
        String url = mainDto.getDriver().getCurrentUrl();
        LOGGER.debug("url = {}", url);
        return url;
    }

    /** navigate().to() will not wait for the page to load
     * @param mainDto
     * @param url
     */
    public static void navigateToURL(MainDto mainDto, String url){
        LOGGER.debug("go to the particular page");
        mainDto.getDriver().navigate().to(url);
        LOGGER.debug("url = {}", url);
    }

    /** navigate to env url
     * @param mainDto
     */
    public static void navigateToURL(MainDto mainDto){
        navigateToURL(mainDto, mainDto.getEnvURL());
    }

    public static void initializePage(MainDto mainDto, Object claz) {
        LOGGER.debug("[Initializing Page]Using time from explicit.wait.limit: {}", SystemUtils.getConfig("explicit.wait.limit"));
        if(!(mainDto.getDriver() == null))
            initializePage(mainDto, claz, AJAX_WAIT_TIME);
    }

    public static void initializePage(MainDto mainDto, Object claz, int waitSeconds) {
        LOGGER.debug("[Initializing Page]: {}", claz);
        PageFactory.initElements(new AjaxElementLocatorFactory(mainDto.getDriver(), waitSeconds), claz);
        waitForPageToLoad(mainDto.getDriver());
    }

    public static void waitForPageToLoad(WebDriver driver) {
        LOGGER.debug("waiting for page to load!");
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIME)).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForPageToLoad(MainDto mainDto) {
        WebDriver driver = mainDto.getDriver();
        waitForPageToLoad(driver);
    }

    public static void refreshPage(MainDto mainDto){
        LOGGER.debug("refreshing page..");
        mainDto.getDriver().navigate().refresh();
    }

    public static void refreshPage(WebDriver driver){
        LOGGER.debug("refreshing page..");
        driver.navigate().refresh();
    }

    public static void navigateBack(WebDriver driver){
        LOGGER.debug("navigating to previous page..");
        driver.navigate().back();
    }

    public static void takeScreenshot(MainDto mainDto) {
        try{
            LOGGER.debug("taking screenshot!");
            if (mainDto.getDriver() == null) {
                LOGGER.debug("will not perform screenshot when driver is null!");
                return;
            }
            if (mainDto.getPlatformType().equals(PlatformTypes.HEADLESS)) {
                LOGGER.debug("will not perform screenshot on headless browsers!");
                return;
            }
            final byte[] screenshot = ((TakesScreenshot) mainDto.getDriver()).getScreenshotAs(OutputType.BYTES);
            mainDto.getScenario().attach(screenshot, "image/png", mainDto.getScenario().getName());
        }
        catch (Exception e){
            LOGGER.debug("general error encountered on screenshot! = {}", e.getMessage());
        }

    }

    public static String getElementText(WebElement webElement) {
        LOGGER.debug("get text");
        return webElement.getText();
    }

    public static void sendKeys(WebElement element, String text) {
        LOGGER.debug("send texts to element = {}", element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * @param mainDto main dto
     * @return driver window's width
     */
    public static int getWindowWidth(MainDto mainDto) {
        LOGGER.debug("getting the current window width");
        int width = mainDto.getDriver().manage().window().getSize().getWidth();
        LOGGER.debug("width = {}", width);
        return width;
    }

    /**
     * @param mainDto main dto
     * @return size of the appium window
     */
    public static Dimension getAppiumWindowSize(MainDto mainDto) {
        LOGGER.debug("getting the current window size of appium driver");
        Dimension size = mainDto.getDriver().manage().window().getSize();
        LOGGER.debug("size = {}", size);
        return size;
    }

    public static void switchToIframe(WebElement element, MainDto mainDto){
        LOGGER.debug("switching to iframe from element = {}", element);
        mainDto.getDriver().switchTo().frame(element);
    }

    public static void switchToIframe(By by, MainDto mainDto){
        LOGGER.debug("switching to iframe from element by = {}", by);
        WebElement element = ElementUtils.findByElementNoWait(mainDto, by);
        mainDto.getDriver().switchTo().frame(element);
    }

    public static void switchToDefaultFrame(MainDto mainDto){
        LOGGER.debug("switching back to default frame!");
        mainDto.getDriver().switchTo().defaultContent();
    }

    public static String getParentWindow(MainDto mainDto){
        LOGGER.debug("get the parent window");
      String parentWindow = mainDto.getDriver().getWindowHandle();
        LOGGER.debug("parentWindow = {}", parentWindow);
       return parentWindow;
    }

    public static Set<String> getAllWindows(MainDto mainDto){
        LOGGER.debug("get the newly opened window");
        Set <String> allWindows = mainDto.getDriver().getWindowHandles();
        LOGGER.debug("allWindows = {}", allWindows);
        return allWindows;
    }

    public static void switchBetweenWindows(MainDto mainDto, String windowHandle){
        LOGGER.debug("switching to another window");
        mainDto.getDriver().switchTo().window(windowHandle);
        LOGGER.debug("switchedWindow = {}", windowHandle);
    }

    public static void backToFirstWindow(MainDto mainDto) {
        List<String> allWindows = new ArrayList<>(DriverUtils.getAllWindows(mainDto));
        switchBetweenWindows(mainDto, allWindows.get(0));
    }

    public static void clickReCaptcha(MainDto mainDto, WebElement captchaElement){
        LOGGER.debug("clicking re captcha!");
        ClickUtils.click(captchaElement);
    }

    /**
     * navigates back on appium driver
     * @param mainDto main dto
     */
    public static void navigateBackAppium(MainDto mainDto) {
        LOGGER.debug("navigating back using appium!");
        mainDto.getDriver().navigate().back();
    }

    /**
     * scrolls down one screen from appium driver
     * @param mainDto main dto
     */
    public static void appiumScrollDownOneScreen(MainDto mainDto) {
        appiumScrollDown(mainDto, 0.6, 0.2);
    }

    public static void appiumScrollUpOneScreen(MainDto mainDto) {
        appiumScrollDown(mainDto, 0.2, 0.6);
    }

    public static void appiumScrollDownHalfScreen(MainDto mainDto) {
        appiumScrollDown(mainDto, 0.6, 0.4);
    }

    /**
     * scrolls down appium driver from and to height relative to the screensize
     * @param mainDto main dto
     * @param from from height relative to the screensize
     * @param to height relative to the screensize
     */
    public static void appiumScrollDown(MainDto mainDto, double from, double to) {
        LOGGER.debug("scrolling an appium driver from = {} to = {} height", from, to);
        var windowSize = DriverUtils.getAppiumWindowSize(mainDto);
        LOGGER.debug("window size = {}", windowSize);
        //TODO use new W3 actions - https://applitools.com/blog/whats-new-appium-java-client-8/
        (new TouchAction((PerformsTouchActions)mainDto.getDriver()))
                .longPress(PointOption.point(windowSize.width / 2, (int) (windowSize.height * from)))
                .moveTo(PointOption.point(windowSize.width / 2, (int) (windowSize.height * to)))
                .release()
                .perform();
    }

    public static void androidScrollToText(MainDto mainDto, String visibleText) {
        LOGGER.debug("scrolling android to visible text = {}", visibleText);
        mainDto.getDriver().findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+visibleText+"\").instance(0))"));
    }


    /**
     * @param mainDto main dto
     * @param setting setting to set
     * @param value value of the setting
     */
//    public static void setAppiumSetting(MainDto mainDto, String setting, Object value){
//        LOGGER.debug("setting appium settings = {} with value = {}", setting, value);
//        mainDto.getDriver().setSetting(setting, value);
//    }

    /**
     * sets an implicit wait for driver. AVOID USING THIS
     * @param mainDto main dto
     * @param seconds seconds of implicit wait
     */
    public static void setImplicitWait(MainDto mainDto, int seconds) {
        LOGGER.debug("setting an implicit wait with {} seconds", seconds);
        if(mainDto.getDriver() != null){
            LOGGER.debug("setting an implicit wait for appium");
            mainDto.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        }
        mainDto.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }
    public static void setImplicitWait(MainDto mainDto) {
        setImplicitWait(mainDto, Integer.parseInt(SystemUtils.getConfig("implicit.wait.limit")));
    }

    /**
     * remove implicit wait
     * @param mainDto main dto
     */
    public static void removeImplicitWait(MainDto mainDto){
        LOGGER.debug("removing implicit wait!");
        setImplicitWait(mainDto, 0);
    }

    /**
     * delete all cookies
     * @param mainDto main dto
     */
    public static void deleteALlCookies(MainDto mainDto){
        LOGGER.debug("deleting all cookies of driver!");
        getCookies(mainDto);
        mainDto.getDriver().manage().deleteAllCookies();
    }

    /**
     * get all cookies
     * @param mainDto main dto
     */
    public static Set<Cookie> getCookies(MainDto mainDto){
        LOGGER.debug("getting all cookies of driver!");
        Set<Cookie> cookies = mainDto.getDriver().manage().getCookies();
        LOGGER.debug("cookies = {}", cookies);
        return cookies;
    }

    /**
     * Add this if you want to capture the network logs. Best added before launching the app
     * @param mainDto
     */
    public static void getRequestsAndResponseUrls(MainDto mainDto) {
        if(mainDto.getDevTools()==null){
            LOGGER.debug("devtools not available! = {}", mainDto.getDevTools());
            return;
        }
        if(Boolean.parseBoolean(SystemUtils.getConfig("network.logs.capture"))){
            DevTools devTools = mainDto.getDevTools();
            LOGGER.debug("getRequestsAndResponseUrls!");
            SystemUtils.createTextFile(SystemUtils.getConfig("network.logs.path"));
            String networkLogsPath = SystemUtils.getConfig("network.logs.path");
            devTools.send(new Command<>("Network.enable", ImmutableMap.of()));
            String dateFormat = "dd-MM-yy-HH:mm:ss";
            devTools.addListener(Network.responseReceived(), l -> {
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat, false) + " Response Status: " + l.getResponse().getStatus()+ " " + l.getResponse().getStatusText(), "\n", networkLogsPath, false);
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat, false) + " Response URL: " + l.getResponse().getUrl(), "\n", networkLogsPath, false);
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat, false) + " Response headers: " + l.getResponse().getHeaders().toString(),"\n", networkLogsPath, false);
            });
            devTools.addListener(Network.requestWillBeSent(), l -> {
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat,false) + " Request METHOD: " + l.getRequest().getMethod(),"\n", networkLogsPath, false);
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat,false) + " Request URL: " + l.getRequest().getUrl(),"\n", networkLogsPath, false);
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat,false) + " Request Headers: " + l.getRequest().getHeaders(),"\n", networkLogsPath, false);
                SystemUtils.appendTextToFile(DateUtils.getCurrentDateTime(dateFormat,false) + " Request Post data: " + l.getRequest().getPostData(),"\n", networkLogsPath, false);
            });
        }
    }

    /**
     * create a new tab and switches to it
     * @param mainDto main dto
     */
    public static void createNewTab(MainDto mainDto){
        LOGGER.debug("creating new window!");
        mainDto.getDriver().switchTo().newWindow(WindowType.TAB);
    }

    /**
     * close tab by window handler
     * @param mainDto main dto
     * @param windowHanlder window handler to close
     */
    public static void closeTab(MainDto mainDto, String windowHanlder){
        LOGGER.debug("closing tab by windowHanlder = {}", windowHanlder);
        mainDto.getDriver().switchTo().window(windowHanlder).close();
    }

    /**
     * creates a new tab and closes the old one
     * @param mainDto main dto
     */
    public static void createNewTabAndCloseCurrent(MainDto mainDto){
        LOGGER.debug("creating a new tab and closes the old one");
        String oldWindow = DriverUtils.getParentWindow(mainDto);
        createNewTab(mainDto);
        String newWindow = DriverUtils.getParentWindow(mainDto);
        closeTab(mainDto, oldWindow);
        switchBetweenWindows(mainDto, newWindow);
    }

    public static boolean isRemoteDriver(MainDto mainDto) {
        LOGGER.debug("checking if driver is remote webdriver");
        boolean isRemoteDriver = mainDto.getDriverType().contains("emote");
        LOGGER.debug("isRemoteDriver = {}", isRemoteDriver);
        return isRemoteDriver;
    }
}