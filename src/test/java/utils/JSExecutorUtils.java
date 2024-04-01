package utils;

import dto.MainDto;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JSExecutorUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSExecutorUtils.class);

    private JSExecutorUtils(){}

    public static Object executeScript(WebDriver driver, String executedScript) {
        Object response;
        if (driver instanceof JavascriptExecutor) {
            response =  ((JavascriptExecutor) driver).executeScript(executedScript);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
        return response;
    }

    public static Object executeScript(MainDto mainDto, String executedScript) {
        return executeScript(mainDto.getDriver(), executedScript);
    }

    public static void executeClickOnElement(WebDriver driver, By by) {
        JSExecutorUtils.executeScript(driver, driver.findElement(by), "arguments[0].click();");
    }

    public static void click(WebDriver driver, WebElement element) {
        LOGGER.debug("clicking element = {} using javascript!", element);
        try{
            JSExecutorUtils.executeScript(driver, element, "arguments[0].click();");
        }
        catch (StaleElementReferenceException e){
            LOGGER.debug("StaleElementReferenceException encountered on click, will try to click again!");
            ElementUtils.getRefreshedStaleElement(driver, element).click();
        }
    }

    /**
     *  click element using javascript
     * @param mainDto main dto
     * @param element element to click
     */
    public static void click(MainDto mainDto, WebElement element) {
        click(mainDto.getDriver(), element);
    }

    /**
     *  mouse over (hover) element using javascript
     * @param driver driver. suggest to use mainDto.getDriver()
     * @param element element to hover
     */
    public static Object hover(WebDriver driver, WebElement element) {
        return executeScript(driver, element, "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);");
    }

    public static Object hover(WebDriver driver, By by) {
        return hover(driver, driver.findElement(by));
    }

    /**
     * returns true if the element is enabled
     * @param mainDto main dto
     * @param element element to check if enabled
     * @return true if the element is enabled otherwise, false
     */
    public static boolean isElementEnabled(MainDto mainDto, WebElement element){
        LOGGER.debug("checking if element is enabled using js! = {}", element);
        Object isEnabledObject = JSExecutorUtils.executeScript(mainDto.getDriver(), element, " return arguments[0].disabled");
        boolean isEnabled = !Boolean.parseBoolean(isEnabledObject.toString());
        LOGGER.debug("is element enabled = {}", isEnabled);
        return isEnabled;
    }

    /**
     * returns true if the element is enabled
     * @param mainDto main dto
     * @param by by locator of the element to check if enabled
     * @return true if the element is enabled otherwise, false
     */
    public static boolean isElementEnabled(MainDto mainDto, By by){
        LOGGER.debug("checking if element is enabled using js! = {}", by);
        return isElementEnabled(mainDto, ElementUtils.findByElement(mainDto, by));
    }

    public static Object executeScript(WebDriver driver, WebElement element, String executedScript) {
        if (driver instanceof JavascriptExecutor) {
            return ((JavascriptExecutor) driver).executeScript(executedScript,element);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }

    /**
     * scrolls to top of the page
     * @param mainDto main dto
     */
    public static Object scrollToTop(MainDto mainDto) {
        LOGGER.debug("Scrolling to top of the page...");
        return executeScript(mainDto,"window.scrollTo(0, 0);");
    }
}