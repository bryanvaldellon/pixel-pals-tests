package utils;

import dto.MainDto;
import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
public class WaitUtils {

    private static final int EXPLICIT_WAIT_TIME = Integer.parseInt(SystemUtils.getConfig("explicit.wait.limit"));
    private static final int SLEEP_IN_MILLIS = Integer.parseInt(SystemUtils.getConfig("customize.sleep.time"));
    private static final int POLL_TIME = Integer.parseInt(SystemUtils.getConfig("customize.poll.time"));
    private static final int FLUENT_WAIT_TIME = Integer.parseInt(SystemUtils.getConfig("customize.fluent.wait.time"));

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitUtils.class);
    /**
     * Method to set the WebDriverWait object
     *
     * @param mainDto main dto that contains the driver
     * @param timeOutInSecs time out in seconds
     * @param sleepInMillis sleep in milliseconds
     * @return returns new WebDriver wait
     */
    public static WebDriverWait setWebDriverWait(MainDto mainDto, int timeOutInSecs, int sleepInMillis) {
        LOGGER.debug("Clock: {}", Clock.systemDefaultZone());
        LOGGER.debug("Sleeper: {}", Sleeper.SYSTEM_SLEEPER);
        LOGGER.debug("Timeout in seconds: {}", timeOutInSecs);
        LOGGER.debug("Sleep in milliseconds: {}", sleepInMillis);
        return new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds(timeOutInSecs), Duration.ofMillis(sleepInMillis), Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER);
    }

    /**
     * Wait until an element is displayed
     * You can provide this method wait with sleep intervals until the visibility of the element
     *
     * @param mainDto main dto that contains the driver
     * @param element element to display
     * @param timeoutInSecs timeout in seconds
     * @param sleepInMillis sleep in millis
     */
    public static WebElement waitUntilElementIsDisplayed(MainDto mainDto, WebElement element, int timeoutInSecs, int sleepInMillis) {
        LOGGER.debug("Waiting for element to be displayed: {}", element);
        //TODO below gives org.openqa.selenium.WebDriverException: unknown error: unhandled inspector error: {"code":-32000,"message":"No node with given id found"}
        // will create a temporary logic for this method https://groups.google.com/g/chromedriver-users/c/ziWgK92VNJY
        // methods of ExpectedConditions are returning the error above
        WebDriverWait wait = setWebDriverWait(mainDto, timeoutInSecs, sleepInMillis);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            LOGGER.debug("found element: {}", element);
        } catch (TimeoutException e) {
           ExceptionUtils.tryCatchExceptionHandler("waitForElementVisibility - ", e);
        }
        catch (StaleElementReferenceException e){
            LOGGER.debug("StaleElementReferenceException encountered, will try to fix!");
            wait.until(ExpectedConditions.elementToBeClickable(ElementUtils.getRefreshedStaleElement(mainDto.getDriver(), element))).isDisplayed();
        }
//        int sleepMultiplier = 1000 / sleepInMillis;
//        timeoutInSecs = timeoutInSecs * sleepMultiplier;
//        LOGGER.debug("waitTime = {}", timeoutInSecs);
//        LOGGER.debug("sleepMultiplier = {}", sleepMultiplier);
//        while(timeoutInSecs > 0){
//            try{
//                if(!element.isDisplayed()){
//                    SystemUtils.threadSleep(sleepInMillis);
//                }
//                else {
//                    break;
//                }
//            }
//            catch (WebDriverException e){
//                LOGGER.debug("StaleElementReferenceException encountered, continueing to wait!");
//                element = ElementUtils.getRefreshedStaleElement(mainDto, element);
//                continue;
//            }
//            timeoutInSecs--;
//        }
//        if(timeoutInSecs <= 0){
//            ExceptionUtils.tryCatchExceptionHandler("element = {} is not displayed even after waiting!");
//        }
        return element;
    }

    /**
     * Wait until an element is displayed
     *
     * @param mainDto main dto that contains the driver
     * @param element element to wait
     * @param waitInSeconds wait in seconds
     */
    public static WebElement waitUntilElementIsDisplayed(MainDto mainDto, WebElement element, int waitInSeconds) {
        return waitUntilElementIsDisplayed(mainDto, element, waitInSeconds, SLEEP_IN_MILLIS);
    }

    public static void waitUntilElementIsDisplayed(MainDto mainDto, By by, int waitInSeconds) {
        waitUntilElementIsDisplayed(mainDto,by,waitInSeconds, SLEEP_IN_MILLIS);
    }


    /**
     * Waits until an element is displayed
     *
     * @param mainDto main dto that contains the driver
     * @param element element to wait
     */
    public static WebElement waitUntilElementIsDisplayed(MainDto mainDto, WebElement element) {
        return waitUntilElementIsDisplayed(mainDto, element, EXPLICIT_WAIT_TIME);
    }

    /**
     * Wait until an element is displayed
     *
     * @param mainDto main dto that contains the driver
     * @param by by locator of the element
     * @param sleepTime sleep time in millis
     * @param waitTime wait time in seconds
     */
    public static WebElement waitUntilElementIsDisplayed(MainDto mainDto, By by, int waitTime, int sleepTime) {
        LOGGER.debug("Waiting for By:{} to be displayed", by);
        WebDriverWait wait = setWebDriverWait(mainDto, waitTime, sleepTime);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            LOGGER.debug("By: {} is not displayed", by);
            ExceptionUtils.tryCatchExceptionHandler("By: "+by+" is not displayed", e);
            throw new AssertionError(e);
        }
    }

    /**
     * Wait until an element is displayed
     *
     * @param mainDto main dto that contains the driver
     * @param by by locator of element
     */
    public static WebElement waitUntilElementIsDisplayed(MainDto mainDto, By by) {
        return waitUntilElementIsDisplayed(mainDto, by, EXPLICIT_WAIT_TIME, SLEEP_IN_MILLIS);
    }

    /**
     * Wait until an element is not displayed/invisible within provided time.
     * @param element WebElement
     */
    public static void waitUntilElementIsNotDisplayed(MainDto mainDto, WebElement element , int sleepTime, int waitTime) {
        LOGGER.debug("Wait until element = {} is not displayed ..", element);
        WebDriverWait wait = setWebDriverWait(mainDto, waitTime, sleepTime);
        try {
            //TODO below gives org.openqa.selenium.WebDriverException: unknown error: unhandled inspector error: {"code":-32000,"message":"No node with given id found"}
            // will create a temporary logic for this method https://groups.google.com/g/chromedriver-users/c/ziWgK92VNJY
            // remove this method by 6-25-30
//            wait.until(ExpectedConditions.invisibilityOf(element));
            int sleepMultiplier = 1000 / sleepTime;
            waitTime = waitTime * sleepMultiplier;
            LOGGER.debug("waitTime = {}", waitTime);
            LOGGER.debug("sleepMultiplier = {}", sleepMultiplier);
            while(element.isDisplayed() && waitTime > 0){
                SystemUtils.threadSleep(sleepTime);
                waitTime--;
            }
            if(waitTime <= 0){
                ExceptionUtils.tryCatchExceptionHandler("element = {} is still displayed even after waiting!");
            }
        }
        catch(NoSuchElementException noSuchElementException){
            LOGGER.debug("NoSuchElementException has been encountered, meaning the element is not displayed already!");
        }
        catch (StaleElementReferenceException staleElementReferenceException){
            LOGGER.debug("StaleElementReferenceException has been encountered, meaning the element is not displayed already!");
        }
        catch (TimeoutException e) {
            LOGGER.debug("element = {} is still visible ", element);
        }
        catch (WebDriverException e) {
            //TODO below gives org.openqa.selenium.WebDriverException: unknown error: unhandled inspector error: {"code":-32000,"message":"No node with given id found"}
            // will create a temporary logic for this method https://groups.google.com/g/chromedriver-users/c/ziWgK92VNJY
            // remove this method by 6-25-30
            LOGGER.debug("known issue for this wait, will ignore for now", e);
        }
    }
    public static void waitUntilElementIsNotDisplayed(MainDto mainDto, WebElement element){
        waitUntilElementIsNotDisplayed(mainDto, element, SLEEP_IN_MILLIS , EXPLICIT_WAIT_TIME);
    }

    public static void waitUntilElementIsNotDisplayed(MainDto mainDto, WebElement element, int waitTime){
        waitUntilElementIsNotDisplayed(mainDto, element, SLEEP_IN_MILLIS , waitTime);
    }

    public static void waitUntilElementsAreNotDisplayed(MainDto mainDto, List<WebElement> element){
        WebDriverWait wait = new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds (10));
        wait.until(ExpectedConditions.invisibilityOfAllElements(element));
        LOGGER.debug("element = {} is not displayed", element);
    }


    /**
     * Wait until element is not displayed
     *
     * @param by by locator of the element
     * @param sleepTime sleep time in millis
     * @param waitTime wait time in seconds
     */
    public static void waitUntilElementIsNotDisplayed(MainDto mainDto, By by, int sleepTime, int waitTime) {
        LOGGER.debug("Wait until By: {} is not displayed ..", by);
        WebDriverWait wait = setWebDriverWait(mainDto, waitTime, sleepTime);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            LOGGER.debug("By: {} is still visible ", by);
        }
    }

    /**
     * Wait until element is not displayed
     *
     * @param by by locator of the element
     */
    public static void waitUntilElementIsNotDisplayed(MainDto mainDto, By by) {
        waitUntilElementIsNotDisplayed(mainDto, by, SLEEP_IN_MILLIS, EXPLICIT_WAIT_TIME);
    }

    /**
     * Wait until an element is not not checked.
     *
     * @param element element to wait
     * @param attribute attribute to wait
     */
    public static void waitUntilElementAttributeContains(MainDto mainDto, WebElement element, String attribute, String valueToContain, int timeoutInSecs, int waitInSeconds) {
        LOGGER.debug("Waiting for element to be unchecked: {}", element);
        WebDriverWait wait = setWebDriverWait(mainDto, timeoutInSecs, waitInSeconds);
        try {
            wait.until(new Function<WebDriver, Boolean>() {
                           public Boolean apply(WebDriver driver) {
                               if (valueToContain.equalsIgnoreCase("null")) {
                                   if (element.getAttribute(attribute) == null) {
                                       return true;
                                   } else {
                                       return false;
                                   }
                               } else {
                                   if (element.getAttribute(attribute).equals(valueToContain)) {
                                       return true;
                                   } else {
                                       return false;
                                   }
                               }

                           }
                       }
            );
        } catch (TimeoutException e) {
            ExceptionUtils.tryCatchExceptionHandler("waitUntilElementAttributeContains - ", e);
        }
    }

    /**
     * Waits until an element is not displayed/invisible
     *
     * @param element element to wait
     * @param waitInSeconds - wait time for the element to be invisible/not displayed
     */
    public static void waitUntilElementAttributeContains(MainDto mainDto, WebElement element, String attribute, String valueToContain, int waitInSeconds) {
        waitUntilElementAttributeContains(mainDto, element, attribute, valueToContain, waitInSeconds, SLEEP_IN_MILLIS);
    }

    /**
     * Wait until an element is not displayed/invisible
     *
     * @param element element to wait
     */
    public static void waitUntilElementAttributeContains(MainDto mainDto, WebElement element, String attribute, String valueToContain) {
        waitUntilElementAttributeContains(mainDto, element, attribute, valueToContain, EXPLICIT_WAIT_TIME);
    }

    /**
     * Waits until an element is displayed
     *
     * @param by by locator of the element
     */
    public static void waitForElementClickable(MainDto mainDto, By by, int seconds) {
        WebDriverWait wait = new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds (seconds));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForElementClickable(MainDto mainDto, WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds (seconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementClickable(MainDto mainDto, By by) {
        waitForElementClickable(mainDto, by, EXPLICIT_WAIT_TIME);
    }

    public static void waitForElementClickable(MainDto mainDto, WebElement element) {
        waitForElementClickable(mainDto,element, EXPLICIT_WAIT_TIME);
    }

    public static void waitForElementToBeClickableAndClick(MainDto mainDto, By by) {
        WebElement element = ElementUtils.findByElementNoWait(mainDto, by);
        waitForElementToBeClickableAndClick(mainDto, element);
    }

    public static void waitForElementToBeClickableAndClick(MainDto mainDto, WebElement element) {
        waitForElementToBeClickableAndClick(mainDto, element, EXPLICIT_WAIT_TIME);
    }

    public static void waitForElementToBeClickableAndClick(MainDto mainDto, WebElement element, int waitTimeInSecs) {
        waitUntilElementIsClickable(mainDto, element, waitTimeInSecs);
        ClickUtils.click(element);
    }

    public static void waitUntilElementIsDisplayedUsingFluentWait(MainDto mainDto, WebElement element, int pollingTime, int waitTime) {
        LOGGER.debug("Waiting until element:{}  is displayed using Fluent wait. Poll Time: {}, Wait Time: {}", element, pollingTime, waitTime);
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilElementIsDisplayedUsingFluentWait(MainDto mainDto, By by, int pollingTime, int waitTime) {
        LOGGER.debug("Waiting until By: {} is displayed using Fluent wait. Poll Time: {}, Wait Time: {}", by, pollingTime, waitTime);
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitUntilElementIsDisplayedUsingFluentWait(MainDto mainDto, By by) {
        waitUntilElementIsDisplayedUsingFluentWait(mainDto, by, POLL_TIME,
                FLUENT_WAIT_TIME);
    }

    public static void waitUntilElementIsDisplayedUsingFluentWait(MainDto mainDto, WebElement element) {
        waitUntilElementIsDisplayedUsingFluentWait(mainDto, element, POLL_TIME,
                FLUENT_WAIT_TIME);
    }

    public static void waitUntilElementIsNotDisplayedUsingFluentWait(MainDto mainDto, WebElement element, int pollingTime, int waitTime) {
        LOGGER.debug("Waiting until Element is not displayed using Fluent wait. Poll Time: {}, Wait Time: {}", pollingTime, waitTime);
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime));
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (NoSuchElementException nse) {
            LOGGER.debug("Element is not displayed: {}", element);
        }
    }

    /**
     * Waits for a specific text to be present in an element
     * Polling time and Wait time is set in config
     *
     * @param element
     * @param expectedText
     */
    public static void waitUntilTextIsPresentInElement(MainDto mainDto, WebElement element, String expectedText) {
        waitUntilTextIsPresentInElement(mainDto, element, expectedText, POLL_TIME,
                FLUENT_WAIT_TIME);
    }

    /**
     * Waits for a specific text to be present in an element
     *
     * @param element
     * @param expectedText
     */
    public static void waitUntilTextIsPresentInElement(MainDto mainDto, WebElement element, String expectedText, int pollingTime, int waitTime) {
        LOGGER.debug("[waitUntilTextIsPresentInElement]Waiting until text[{}] is present in element[{}]. Current Element text: {}", expectedText, element, element.getText());
        LOGGER.debug("[waitUntilTextIsPresentInElement]Wait poll time: {} and Wait Time: {}", pollingTime, waitTime);
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime));
        try {
            wait.until((ExpectedConditions.textToBePresentInElementValue(element, expectedText)));
        } catch (TimeoutException te) {
            LOGGER.debug("Expected Text: [{}] is not displayed in Element:[{}]. Current Element Text: {}", expectedText, element, element.getText());
        }
    }

    public static void waitUntilTextIsPresentInElement(MainDto mainDto, By by, String expectedText, int pollingTime, int waitTime) {
        LOGGER.debug("[waitUntilTextIsPresentInElement]Waiting until text[{}] is present in By[{}]", expectedText, by);
        LOGGER.debug("[waitUntilTextIsPresentInElement]Wait poll time: {} and Wait Time: {}", pollingTime, waitTime);
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime));
        try {
            wait.until((ExpectedConditions.textToBePresentInElementValue(by, expectedText)));
        } catch (TimeoutException te) {
            LOGGER.debug("Expected Text: [{}] is not displayed in Element:[{}]", expectedText, by);
        }
    }

    /**
     * Waits for a specific text to change with refresh of page
     *
     * @param element
     * @param expectedText
     */

    public static void waitUntilTextInWebElementChangesToExpected(MainDto mainDto, WebElement element, String expectedText, int pollingTime, int waitTime) {
        LOGGER.debug("[waitUntilTextIsPresentInWebElement]Waiting until text[{}] is present in element[{}]", expectedText, element.getText());
        LOGGER.debug("[waitUntilTextIsPresentInWebElement]Wait poll time: {} and Wait Time: {}", pollingTime, waitTime);
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime));
        try {
            wait.until(textToBeVisibleWithRefreshPage(element, expectedText));
        } catch (TimeoutException te) {
            LOGGER.debug("Expected Text: [{}] is not displayed in Web Element:[{}]", expectedText, element);
        }
    }

    public static ExpectedCondition<Boolean> textToBeVisibleWithRefreshPage(WebElement element, String expectedText) {
        return new ExpectedCondition<Boolean>() {
            private boolean isPresent = false;

            @Override
            public Boolean apply(WebDriver driver) {
                String text = ElementUtils.getText(element);
                if (text.contains(expectedText)) {
                    isPresent = true;
                    LOGGER.debug("Expected Text: [{}] is present in Web Element:[{}]", expectedText, element);
                }
                if (!isPresent) {
                    DriverUtils.refreshPage(driver);
                    //sleeping after refreshing for element to be intractable in DOM
                    SystemUtils.threadSleep(2000);
                }
                return isPresent;
            }
        };
    }

    public static void waitUntilTextInWebElementChangesToExpected(MainDto mainDto, WebElement element, String expectedText) {
        waitUntilTextInWebElementChangesToExpected(mainDto, element, expectedText, POLL_TIME,
                FLUENT_WAIT_TIME);
    }

    public static void waitUntilTextIsPresentInElement(MainDto mainDto, By by, String expectedText) {
        waitUntilTextIsPresentInElement(mainDto, by, expectedText, POLL_TIME,
                FLUENT_WAIT_TIME);
    }

    public static void waitUntilElementIsClickable(MainDto mainDto, WebElement element, int pollingTime, int waitTime) {
        Wait<WebDriver> wait = new FluentWait<>(mainDto.getDriver())
                // Check for condition in every - pollingTime
                .pollingEvery(Duration.ofSeconds(pollingTime))
                // Till time out - waitTime
                .withTimeout(Duration.ofSeconds(waitTime));
        try {
            wait.until((ExpectedConditions.elementToBeClickable(element)));
        }
        catch (TimeoutException te) {
            LOGGER.debug("Element:[{}] is not clickable ", element);
        }
        catch (StaleElementReferenceException e){
            LOGGER.debug("StaleElementReferenceException encountered on wait, will try to refresh element and wait again!");
            wait.until((ExpectedConditions.elementToBeClickable(ElementUtils.getRefreshedStaleElement(mainDto.getDriver(), element))));
        }
        catch (ElementClickInterceptedException e){
            LOGGER.debug("ElementClickInterceptedException encountered on wait, will try to click element until it is visible!");
            wait.until((ExpectedConditions.visibilityOfElementLocated((By) element)));
        }
    }

    public static void waitUntilElementIsClickable(MainDto mainDto, WebElement element) {
        waitUntilElementIsClickable(mainDto, element, POLL_TIME,
                FLUENT_WAIT_TIME);
    }

    public static void waitUntilElementIsClickable(MainDto mainDto, WebElement element, int waitTimeInSecs) {
        waitUntilElementIsClickable(mainDto, element, POLL_TIME,
                waitTimeInSecs);
    }
    public static void waitUntilElementsAreClickable(MainDto mainDto, List<WebElement> elements) {
        LOGGER.debug("waiting for list of elements = {}", elements);
        for(WebElement element: elements){
            waitUntilElementIsClickable(mainDto, element, POLL_TIME,
                    FLUENT_WAIT_TIME);
        }
    }

    public static void waitForAlerts(MainDto mainDto, int waitTimeInSecs){
        try{
            LOGGER.debug("waiting for alerts secs = {}", waitTimeInSecs);
            WebDriverWait wait = new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds(waitTimeInSecs));
            wait.until(ExpectedConditions.alertIsPresent());
        }
        catch (TimeoutException e){
            LOGGER.debug("alert not found!");
        }

    }

    public static void waitForAlerts(MainDto mainDto){
        waitForAlerts(mainDto, EXPLICIT_WAIT_TIME);
    }
}