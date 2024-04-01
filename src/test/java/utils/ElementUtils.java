package utils;

import dto.MainDto;
import enums.Currency;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.util.List;

public class ElementUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementUtils.class);
    private static final int WAIT_TIME = Integer.parseInt(SystemUtils.getConfig("explicit.wait.limit"));
    private static final int SCROLL_LIMIT = Integer.parseInt(SystemUtils.getConfig("scroll.limit"));
    private static final int SLEEP_IN_MILLIS = Integer.parseInt(SystemUtils.getConfig("customize.sleep.time"));

    /**
     * Checks if the element is displayed
     * @param element element to check if displayed
     * @param mainDto main dto
     * @return returns true if the element is displayed with explicit wait
     */
    public static boolean isElementDisplayed(MainDto mainDto, WebElement element, int waitTimeInSeconds) {
        LOGGER.debug("Checking if WebElement: {} is displayed", element);
        if (element == null) {
            LOGGER.debug("Element is null ..");
            return false;
        }
        WebDriverWait wait = new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds(waitTimeInSeconds));
        try {
            boolean isElementDisplayed = wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
            LOGGER.debug("element is displayed? = {}", isElementDisplayed);
            return isElementDisplayed;
        }
        catch (TimeoutException | NoSuchElementException | IndexOutOfBoundsException exception) {
            LOGGER.debug("Element: {} is not displayed", element);
            return false;
        }
        catch (StaleElementReferenceException e){
            LOGGER.debug("StaleElementReferenceException encountered, will try to fix!");
            return wait.until(ExpectedConditions.elementToBeClickable(getRefreshedStaleElement(mainDto, element))).isDisplayed();
        }
    }

    public static boolean isElementDisplayed(MainDto mainDto, WebElement element){
        return isElementDisplayed(mainDto, element, WAIT_TIME);
    }

    /**
     * returns true if at least one element from the list is displayed
     * @param mainDto main dto
     * @param elements list of elements to check
     * @return returns true if at least one element from the list is displayed
     */
    public static boolean isAtLeastOneElementDisplayed(MainDto mainDto, List<WebElement> elements, int waitTime){
        LOGGER.debug("checking if list of elements = {} if one of them is displayed", elements);
        for(WebElement element : elements){
            if(isElementDisplayed(mainDto, element, waitTime)){
                LOGGER.debug("element = {} found!", element);
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if at least one element from the list is displayed
     * @param mainDto main dto
     * @param elements list of elements to check
     * @return returns true if at least one element from the list is displayed
     */
    public static boolean isAtLeastOneElementDisplayed(MainDto mainDto, List<WebElement> elements){
        return isAtLeastOneElementDisplayed(mainDto, elements, WAIT_TIME);
    }

    /**
     * Checks if the element is displayed
     * @param by element by locator
     * @param mainDto main dto
     * @param waitTimeInSecs wait time in secs
     * @return returns true if the element is displayed with explicit wait
     */
    public static boolean isElementDisplayed(MainDto mainDto, By by, int waitTimeInSecs){
        return isElementDisplayed(mainDto, by, waitTimeInSecs, SLEEP_IN_MILLIS);
    }

    /**
     * Checks if the element is displayed
     * @param by element by locator
     * @param mainDto main dto
     * @param waitTimeInSecs wait time in secs
     * @param pollingTimeInMillis polling time in milliseconds
     * @return returns true if the element is displayed with explicit wait
     */
    public static boolean isElementDisplayed(MainDto mainDto, By by, int waitTimeInSecs, int pollingTimeInMillis){
        LOGGER.debug("isElementDisplayed by = {}", by);
        try {
            WebDriverWait wait = new WebDriverWait(mainDto.getDriver(),
                    Duration.ofSeconds(waitTimeInSecs),
                    Duration.ofMillis(pollingTimeInMillis),
                    Clock.systemDefaultZone(),
                    Sleeper.SYSTEM_SLEEPER);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        }
        catch (TimeoutException | NoSuchElementException | IndexOutOfBoundsException exception) {
            LOGGER.debug("Element by: {} is not displayed", by);
            return false;
        }
    }

    /**
     * Checks if the element is displayed
     * @param by element by locator
     * @param mainDto main dto
     * @return returns true if the element is displayed with explicit wait
     */
    public static boolean isElementDisplayed(MainDto mainDto, By by){
        return isElementDisplayed(mainDto, by, WAIT_TIME);
    }

    public static boolean isElementsDisplayed(MainDto mainDto, List<WebElement> elements){
        return isElementsDisplayed(mainDto, elements, WAIT_TIME);
    }

    public static boolean isElementsDisplayed(MainDto mainDto, List<WebElement> elements, int waitTime){
        LOGGER.debug("checking if list of elements are displayed = {}", elements);
        boolean isDisplayed = false;
        if(elements.size() < 1 || elements.isEmpty()){
            LOGGER.debug("list is empty! element is not displayed!");
            return false;
        }
        else{
            for(WebElement element: elements){
                isDisplayed = isElementDisplayed(mainDto, element, waitTime);
            }
        }
        return isDisplayed;
    }

    /**
     * Finds again the element from the previous DOM
     * @param mainDto main dto
     * @param element element to refresh
     * @return returns refreshed element
     */
    public static WebElement getRefreshedStaleElement(MainDto mainDto, WebElement element){
        LOGGER.debug("refreshing element from DOM = {}", element);
        return mainDto.getDriver().findElement(getElementLocator(element));
    }

    public static WebElement getRefreshedStaleElement(WebDriver driver, WebElement element){
        LOGGER.debug("refreshing element from DOM = {}", element);
        return driver.findElement(getElementLocator(element));
    }

    /**
     * Retrieves the locator or the web element
     * @param element element to find the locator
     * @return returns By locator of the element
     */
    public static By getElementLocator(WebElement element){
        LOGGER.debug("getting web element locator of = {}", element);
        String elementString = element.toString();
        By by = null;
        String locator = "";
        if(elementString.contains("id:")){
            locator = elementString.substring(elementString.indexOf("id:") + 3, elementString.length()-1);
            by = new By.ById(locator);
        }
        if(elementString.contains("xpath:")){
            locator = elementString.substring(elementString.indexOf("xpath:") + 7, elementString.length()-1);
            by = new By.ByXPath(locator);
        }
        if(elementString.contains("name:")){
            locator = elementString.substring(elementString.indexOf("name:") + 5, elementString.length()-1);
            by = new By.ByName(locator);
        }
        LOGGER.debug("locator = {}", locator);
        return by;
    }

    /**
     * checks the element if displayed without waiting
     * @param element element to check if displayed
     * @return returns true if the element is displayed
     */
    public static boolean isElementDisplayedNoWait(WebElement element) {
        LOGGER.debug("Checking if WebElement : {} is displayed", element);
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException exception) {
            LOGGER.debug("Element : {} is not displayed: ", element);
        }
        return false;
    }

    /**
     * checks the element if displayed without waiting
     * @param by element locator to check if displayed
     * @return returns true if the element is displayed
     */
    public static boolean isElementDisplayedNoWait(MainDto mainDto, By by) {
        try{
            WebElement element = findByElementNoWait(mainDto, by);
            return isElementDisplayedNoWait(element);
        }
        catch (NoSuchElementException e){
            LOGGER.debug("isElementDisplayedNoWait by = {} is not displayed!", by);
            return false;
        }

    }

    /**
     * Get above relative element
     * @param mainDto main dto
     * @param fromElement element reference
     * @param xpath tag of the element you want to get
     * @return returns above relative element
     */
    public static WebElement getAboveElement(MainDto mainDto, WebElement fromElement, String xpath){
        LOGGER.debug("getting above element from = {} , with xpath of = {}", fromElement , xpath);
        WebElement element = mainDto.getDriver().findElement(RelativeLocator.with(By.xpath(xpath)).above(fromElement));
        LOGGER.debug("above element = {}", element);
        return element;
    }

    /**
     * Get below relative element
     * @param mainDto main dto
     * @param fromElement element reference
     * @param xpath tag of the element you want to get
     * @return returns below relative element
     */
    public static WebElement getBelowElement(MainDto mainDto, WebElement fromElement, String xpath){
        LOGGER.debug("getting below element from = {} , with xpath of = {}", fromElement , xpath);
        WebElement element = mainDto.getDriver().findElement(RelativeLocator.with(By.xpath(xpath)).below(fromElement));
        LOGGER.debug("below element = {}", element);
        return element;
    }

    /**
     * Get left relative element
     * @param mainDto main dto
     * @param fromElement element reference
     * @param xpath tag of the element you want to get
     * @return returns left relative element
     */
    public static WebElement getLeftElement(MainDto mainDto, WebElement fromElement, String xpath){
        LOGGER.debug("getting left element from = {} , with xpath of = {}", fromElement , xpath);
        WebElement element = mainDto.getDriver().findElement(RelativeLocator.with(By.xpath(xpath)).toLeftOf(fromElement));
        LOGGER.debug("left element = {}", element);
        return element;
    }

    /**
     * Get right relative element
     * @param mainDto main dto
     * @param fromElement element reference
     * @param xpath tag of the element you want to get
     * @return returns right relative element
     */
    public static WebElement getRightElement(MainDto mainDto, WebElement fromElement, String xpath){
        LOGGER.debug("getting right element from = {} , with xpath of = {}", fromElement , xpath);
        WebElement element = mainDto.getDriver().findElement(RelativeLocator.with(By.xpath(xpath)).toRightOf(fromElement));
        LOGGER.debug("right element = {}", element);
        return element;
    }

    /**
     * Get parent element
     * @param fromElement element reference
     * @return returns parent element
     */
    public static WebElement getParentElement(WebElement fromElement){
        LOGGER.debug("getting parent element from = {}", fromElement);
        WebElement element = fromElement.findElement(By.xpath("./.."));
        LOGGER.debug("parent element = {}", element);
        return element;
    }

    /**
     * Get first child element with tag
     * @param fromElement element reference
     * @param tag tag of child element
     * @return returns first child element of tag
     */
    public static WebElement getFirstChildElementByTag(WebElement fromElement, String tag){
        LOGGER.debug("getting child element from = {}", fromElement);
        String pathToChild = tag + ":first-child";
        WebElement element = fromElement.findElement(By.cssSelector(pathToChild));
        LOGGER.debug("child element with <{}> tag = {}", tag, element);
        return element;
    }

    /**
     * Get Nth child element
     * @param fromElement element reference
     * @param index index of child
     * @return returns child element of index
     */
    public static WebElement getChildElementOfIndex(WebElement fromElement, int index){
        LOGGER.debug("getting child element from = {}", fromElement);
        String indexStr = String.valueOf(index);
        String pathToChild =  "child::*[" + indexStr + "]";
        WebElement element = fromElement.findElement(By.xpath(pathToChild));
        LOGGER.debug("child index {} child of element = {}", indexStr, element);
        return element;
    }

    /**
     * Get next No.N sibling element
     * @param fromElement element reference
     * @param indexN order of next sibling you want to get, e.g. 1 means next sibling, 2 means next of next, etc.
     * @return returns next No.N sibling element
     */
    public static WebElement getNextNSiblingElement(WebElement fromElement,int indexN){
        LOGGER.debug("getting next No.{} sibling element from = {}", indexN, fromElement);
        String indexNStr=String.valueOf(indexN);
        String nextNSiblingRelativePath = "following-sibling::*" + "[" + indexNStr + "]";
        WebElement element = fromElement.findElement(By.xpath(nextNSiblingRelativePath));
        LOGGER.debug("next No.{} sibling element = {}", indexN, element);
        return element;
    }

    /**
     * Get preceding No.N sibling element
     * @param fromElement element reference
     * @param indexN order of preceding sibling you want to get, e.g. 1 means previous sibling, 2 means previous of previous, etc.
     * @return returns preceding No.N sibling element
     */
    public static WebElement getPrecedingNSiblingElement(WebElement fromElement,int indexN){
        LOGGER.debug("getting preceding No.{} sibling element from = {}", indexN, fromElement);
        String indexNStr=String.valueOf(indexN);
        String prevNSiblingRelativePath = "preceding-sibling::*" + "[" + indexNStr + "]";
        WebElement element = fromElement.findElement(By.xpath(prevNSiblingRelativePath));
        LOGGER.debug("preceding No.{} sibling element = {}", indexN, element);
        return element;
    }

    /**
     * Scroll to the specified element
     * Checks first if the element is displayed before scrolling to it
     *
     * @param element element to scroll to
     */
    public static void scrollToElement(MainDto mainDto, WebElement element) {
        LOGGER.debug("scrolling to element = {}", element);
        if (isElementDisplayed(mainDto, element)) {
            JSExecutorUtils.executeScript(mainDto.getDriver(), element, "arguments[0].scrollIntoView(true)");
        } else {
            throw new NoSuchElementException("Cannot scroll to the element");
        }
    }
    /**
     * Scroll to the specified element
     * Checks first if the element is displayed before scrolling to it
     *
     * @param by element locator to scroll to
     */
    public static void scrollToElement(MainDto mainDto, By by) {
        WebElement element = findByElementNoWait(mainDto, by);
        scrollToElement(mainDto, element);
    }

    /**
     * scrolling to the element using arrow down
     * @param mainDto main dto
     * @param element element to find
     */
    public static void scrollToElementUsingArrowDown(MainDto mainDto, WebElement element){
        LOGGER.debug("scrollToElementUsingArrowDown = {}", element);
        Actions actions = new Actions(mainDto.getDriver());
        int limit = SCROLL_LIMIT;
        while(!ElementUtils.isElementDisplayedNoWait(element) && limit > 0){
            LOGGER.debug("scrolling count = {}", limit);
            actions.sendKeys(Keys.ARROW_DOWN).build().perform();
            limit--;
        }
    }

    /**
     * Method to get the text of the specific element
     * This will wait for the element to be displayed in a given seconds before performing getAttribute
     * Will return null if the attribute is not available in the element
     *
     * @param element element to get attribute
     * @param attribute attribute name
     * @return returns element attribute
     */
    public static String getElementAttribute(WebElement element, String attribute) {
        LOGGER.info("Getting Attribute of Element =  {}", element);
        LOGGER.info("Attribute to find =  {}", attribute);
        LOGGER.info("Is Attribute available = {}", isAttributeAvailable(element, attribute));
        if (isAttributeAvailable(element, attribute)) {
            return element.getAttribute(attribute);
        } else {
            LOGGER.info("Attribute[{}] is not available", attribute);
        }
        return null;
    }

    /**
     * Method to get the text of the specific element
     * This will wait for the element to be displayed in a given seconds before performing getAttribute
     * Will return null if the attribute is not available in the element
     *
     * @param by element to get attribute
     * @param attribute attribute name
     * @return returns element attribute
     */
    public static String getElementAttribute(MainDto mainDto, By by, String attribute){
        WebElement element = findByElement(mainDto, by);
        return getElementAttribute(element, attribute);
    }

    /**
     * Method to check if an attribute is available in the element
     *
     * @param element element to find attribute
     * @param attribute attribute to check
     * @return returns true if attribute is available in the element
     */
    public static boolean isAttributeAvailable(WebElement element, String attribute) {
        String elementAttrib = element.getAttribute(attribute);
        if (elementAttrib == null) {
            LOGGER.debug("Element= {} has no Attribute= {}", element, attribute);
            return false;
        } else {
            LOGGER.debug("Element= {} has Attribute= {}", element, attribute);
            LOGGER.debug("Attribute value= {}", elementAttrib);
            return true;
        }
    }

    /**
     * @param mainDto main dto
     * @param by locator of the element
     * @return webelement found
     */
    public static WebElement findByElement(MainDto mainDto, By by){
        LOGGER.debug("finding by element by = {}", by);
        return WaitUtils.waitUntilElementIsDisplayed(mainDto,by);
    }

    /**
     * find by list of elements without wait
     * @param mainDto maind dto
     * @param by locator of the list of elements
     * @return list of elements found
     */
    public static List<WebElement> findByElementsNoWait(MainDto mainDto, By by){
        LOGGER.debug("finding list of elements by = {}", by);
        return mainDto.getDriver().findElements(by);
    }

    public static WebElement findByElementNoWait(MainDto mainDto, By by){
        LOGGER.debug("finding by element by = {}", by);
        return mainDto.getDriver().findElement(by);
    }

    /**
     * Checks if the element is enabled to click
     * @param element element to check if displayed
     * @param mainDto main dto
     * @return returns true if the element is displayed with explicit wait
     */
    public static boolean isElementEnabled(MainDto mainDto, WebElement element, int waitTimeInSeconds) {
        LOGGER.debug("Checking if WebElement: {} is enabled", element);
        if (element == null) {
            LOGGER.debug("Element is null ..");
            return false;
        }
        WebDriverWait wait = new WebDriverWait(mainDto.getDriver(), Duration.ofSeconds(waitTimeInSeconds));
        try {
            boolean isElementEnabled = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            if(ElementUtils.getElementAttribute(element, "class").contains("disabled")){
                return false;
            }
            LOGGER.debug("element is enabled? = {}", isElementEnabled);
            return isElementEnabled;
        }
        catch (TimeoutException | NoSuchElementException | IndexOutOfBoundsException exception) {
            LOGGER.debug("Element: {} is not enabled", element);
            return false;
        }
        catch (StaleElementReferenceException e){
            LOGGER.debug("StaleElementReferenceException encountered, will try to fix!");
            return wait.until(ExpectedConditions.elementToBeClickable(getRefreshedStaleElement(mainDto, element))).isDisplayed();
        }
    }

    public static boolean isElementsEnabled(MainDto mainDto, List<WebElement> elements){
        LOGGER.debug("checking if list of elements are enabled = {}", elements);
        boolean isEnabled = false;
        if(elements.size() < 1){
            LOGGER.debug("list is empty! element is not enabled!");
            return false;
        }
        else{
            for(WebElement element: elements){
                isEnabled = isElementEnabled(mainDto, element);
            }
        }
        return isEnabled;
    }

    public static boolean isElementEnabled(MainDto mainDto, WebElement element){
        return isElementEnabled(mainDto, element, WAIT_TIME);
    }

    public static boolean isElementEnabled(MainDto mainDto, By by){
        return isElementEnabled(mainDto, findByElementNoWait(mainDto, by), WAIT_TIME);
    }

    public static String getText(WebElement element) {
        LOGGER.debug("getting text of element = {}", element);
        String text = element.getText();
        LOGGER.debug("text = {}", text);
        return text;
    }

    /**
     * gets the float value of an amount with percent + unit. eg. 5.00% APY, result = 5.0
     * @param element element to get text to
     * @param unit string unit to be ignored
     * @return returns float value
     */
    public static float getAmountFromAmountWithPercentUnit(WebElement element,String unit){
        LOGGER.debug("getting amount from amount with percent = {}", element);
        String amount = getText(element);
        if(amount.contains(unit))
            amount = amount.replace(unit, "");
        if(amount.contains("%"))
            amount = amount.replace("%", "");
        float floatAmount = Float.parseFloat(amount);
        LOGGER.debug("amount in float = {}", floatAmount);
        return floatAmount;
    }

    /**
     * get a screenshot of a web element
     * @param element element to screenshot
     * @param filename file name for the file to be generated
     * @return file path of the saved file
     */
    public static String getScreenshot(WebElement element, String filename) {
        LOGGER.debug("getting screehshot of the web element = {}", element);
        File source = element.getScreenshotAs(OutputType.FILE);
        String filepath = "target/screenshots/"+filename+".png";
        File dest = new File(filepath);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return filepath;
    }

    /**
     * get index of the first element displayed on the list
     * @param mainDto main dto
     * @param elements element list
     * @return
     */
    public static int getFirstElementDisplayedIndex(MainDto mainDto, List<WebElement> elements) {
        LOGGER.debug("getting index of the first element displayed on the list! = {}", elements);
        int index = 0;
        for(WebElement element : elements){
            if(ElementUtils.isElementDisplayed(mainDto, element)){
                LOGGER.debug("found element displayed on index = {}", index);
                return index;
            }
            index++;
        }
        LOGGER.debug("element is not displayed will return 0!");
        return 0;
    }
}
