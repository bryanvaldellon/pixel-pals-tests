package utils;

import dto.MainDto;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class AssertionUtils {

    private AssertionUtils(){

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AssertionUtils.class);

    public static void assertEquals(String actual, String expected, String errorMessage) {
        LOGGER.debug("comparing 2 text! actual = {} and expected= {}", actual, expected);
        boolean isEqual = actual.equals(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler("actual = "+actual+" and expected= "+expected+" are not equal! errorMessage = " + errorMessage);
        }
    }

    public static void assertStringEqualsIgnoreCase(String actual, String expected, String errorMessage) {
        LOGGER.debug("comparing 2 text! actual = {} and expected= {}", actual, expected);
        boolean isEqual = actual.equalsIgnoreCase(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler("actual = "+actual+" and expected= "+expected+" are not equal with ignore case! errorMessage = " + errorMessage);
        }
    }

    public static void assertNotEquals(String actual, String expected, String errorMessage) {
        LOGGER.debug("comparing 2 text! actual = {} and expected= {}", actual, expected);
        boolean isEqual = actual.equals(expected);
        if(isEqual){
            ExceptionUtils.tryCatchExceptionHandler("actual = "+actual+" and expected= "+expected+" are equal! errorMessage = " + errorMessage);
        }
    }

    public static void assertEquals(String actual, String expected) {
        LOGGER.debug("comparing 2 text! actual = {} and expected= {}", actual, expected);
        boolean isEqual = actual.equals(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler("actual = "+actual+" and expected= "+expected+" are not equal!");
        }
    }

    public static void assertEquals(Object actual, Object expected, String errorMessage) {
        LOGGER.debug("comparing 2 Object! actual = {} and expected= {}", actual, expected);
        boolean isEqual = actual.equals(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler("actual = "+actual+" and expected= "+expected+" are not equal! errorMessage = " + errorMessage);
        }
    }

    public static void assertEquals(Object actual, Object expected) {
        LOGGER.debug("comparing 2 Object! actual = {} and expected= {}", actual, expected);
        boolean isEqual = actual.equals(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler("actual = "+actual+" and expected= "+expected+" are not equal!");
        }
    }

    /**
     * asserts whether 2 amount are same when both convert to float. e.g. If actual = 10.0, expected = 10, assert pass
     * @param actual string with an amount form, e.g. 10.0
     * @param expected string with an amount form, e.g. 10
     */
    public static void assertStringAmountEquals(String actual, String expected, String errorMessage) {
        LOGGER.debug("comparing 2 string amount! actual = {} and expected= {}", actual, expected);
        boolean isEqual = Float.parseFloat(actual) == Float.parseFloat(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler(errorMessage);
        }
        Assert.assertEquals(Float.parseFloat(actual), Float.parseFloat(expected));
    }

    public static void assertStringAmountEquals(String actual, String expected) {
        LOGGER.debug("comparing 2 string amount! actual = {} and expected= {}", actual, expected);
        boolean isEqual = Float.parseFloat(actual) == Float.parseFloat(expected);
        if(!isEqual){
            ExceptionUtils.tryCatchExceptionHandler("comparing 2 string amount! actual = "+actual+" and expected= "+expected+". not equal!");
        }
        Assert.assertEquals(Float.parseFloat(actual), Float.parseFloat(expected));
    }

    public static void assertTrue(boolean isTrue, String errorMessage) {
        LOGGER.debug("asserting to true = {}", isTrue);
        if(!isTrue){
            ExceptionUtils.tryCatchExceptionHandler(errorMessage);
        }
    }

    public static void assertStringContains(String target, String contains, String errorMessage){
        LOGGER.debug("checking if string = {} contains = {}", target, contains);
        assertTrue(target.contains(contains), errorMessage);
    }

    public static void assertStringContains(String target, String contains){
        LOGGER.debug("checking if string = {} contains = {}", target, contains);
        String errorMessage = target + " does not contains = " + contains;
        assertTrue(target.contains(contains), errorMessage);
    }

    public static void assertStringNotContain(String target, String contains, String errorMessage){
        LOGGER.debug("checking if string = {} does not contain = {}", target, contains);
        assertFalse(target.contains(contains), errorMessage);
    }

    public static void assertFalse(boolean isFalse, String errorMessage) {
        LOGGER.debug("checking if = {}", isFalse);
        if(isFalse){
            ExceptionUtils.tryCatchExceptionHandler(errorMessage);
        }
    }

    public static void assertNotNull(Object object, String errorMessage) {
        LOGGER.debug("Error message : {}, object: {}", errorMessage, object);
        if(object == null){
            ExceptionUtils.tryCatchExceptionHandler(errorMessage);
        }
    }

    public static void assertNull(String errorMessage, Object object) {
        LOGGER.debug("Error message : {}, object: {}", errorMessage, object);
        if(object != null){
            ExceptionUtils.tryCatchExceptionHandler(errorMessage);
        }
    }

    public static void assertIsDisplayed(MainDto mainDto, WebElement element, String errorMessage){
        LOGGER.debug("asserting if element = {} is displayed", element);
        boolean isDisplayed = ElementUtils.isElementDisplayed(mainDto, element);
        LOGGER.debug("element = {} displayed = {}", element, isDisplayed);
        assertTrue(isDisplayed, errorMessage);
    }

    public static void assertMultipleElementsAreDisplayed(MainDto mainDto, List<WebElement> elements, int expectedNoOfElements, String errorMessage){
        LOGGER.debug("asserting if elements = {} are displayed", elements);
        boolean isDisplayed = ElementUtils.isElementsDisplayed(mainDto, elements);
        AssertionUtils.assertTrue(isDisplayed, errorMessage);
        boolean expectedElementsDisplayed = false;
        if(elements.size()==expectedNoOfElements){
            expectedElementsDisplayed=true;
            LOGGER.debug("elements = {} displayed = {}", elements, isDisplayed);
        }
        AssertionUtils.assertTrue(expectedElementsDisplayed, errorMessage);
    }

    public static void assertAtLeastOneIsDisplayed(MainDto mainDto, List<WebElement> elements, String errorMessage){
        LOGGER.debug("asserting if at least one of elements = {} are displayed", elements);
        boolean expectedElementDisplayed = false;
        for (WebElement e: elements){
            if (ElementUtils.isElementDisplayed(mainDto, e) == true) {
                LOGGER.debug("element = {} displayed !", e);
                expectedElementDisplayed = true;
                break;
            }
        }
        AssertionUtils.assertTrue(expectedElementDisplayed, errorMessage);
    }

    public static void assertAllIsDisplayed(MainDto mainDto, List<WebElement> elements, String errorMessage){
        LOGGER.debug("asserting if all of the elements = {} are displayed", elements);
        for (WebElement e: elements){
            AssertionUtils.assertIsDisplayed(mainDto,e,errorMessage);
        }
    }

    public static void assertIsDisplayed(MainDto mainDto, By by, String errorMessage){
        LOGGER.debug("asserting if element by = {} is displayed", by);
        WebElement element = ElementUtils.findByElement(mainDto, by);
        boolean isDisplayed = ElementUtils.isElementDisplayed(mainDto, element);
        LOGGER.debug("element = {} displayed = {}", element, isDisplayed);
        assertTrue(isDisplayed, errorMessage);
    }

    public static void assertIsNotDisplayed(MainDto mainDto, By by, int waitTime, String errorMessage){
        LOGGER.debug("asserting if element by = {} is displayed", by);
        boolean isDisplayed = true;
        try {
            WaitUtils.waitUntilElementIsDisplayed(mainDto, by, waitTime);
        } catch (AssertionError e) {
            isDisplayed = false;
        }
        LOGGER.debug("By = {} displayed = {}", by, isDisplayed);
        assertFalse(isDisplayed, errorMessage);
    }

    public static void assertIsNotDisplayed(MainDto mainDto, WebElement element, String errorMessage){
        LOGGER.debug("asserting if element by = {} is not displayed", element);
        boolean isDisplayed = ElementUtils.isElementDisplayed(mainDto, element);
        LOGGER.debug("element = {} displayed = {}", element, isDisplayed);
        assertFalse(isDisplayed, errorMessage);
    }

    public static void assertIsNotDisplayed(MainDto mainDto, List<WebElement> elements, String errorMessage){
        LOGGER.debug("asserting list of elements = {} if they are not displayed", elements);
        for(WebElement element : elements){
            assertIsNotDisplayed(mainDto, element, errorMessage);
        }
    }

    public static void assertIsDisplayed(MainDto mainDto, String xpath, String errorMessage){
        LOGGER.debug("asserting if element by xpath = {} is displayed", xpath);
        assertIsDisplayed(mainDto, By.xpath(xpath), errorMessage);
    }

    /**
     * @param mainDto main dto
     * @param element element to assert if enabled
     */
    public static void assertIsEnabled(MainDto mainDto, WebElement element, String errorMessage) {
        LOGGER.debug("asserting element = {} if enabled", element );
        assertTrue(ElementUtils.isElementEnabled(mainDto, element), errorMessage);
    }

    /**
     * @param mainDto main dto
     * @param by by locator to assert if is enabled
     */
    public static void assertIsEnabled(MainDto mainDto, By by, String errorMessage) {
        LOGGER.debug("asserting element by = {} if enabled", by );
        WebElement element = ElementUtils.findByElement(mainDto,by);
        assertTrue(ElementUtils.isElementEnabled(mainDto, element), errorMessage);
    }

    /**
     * @param mainDto main dto
     * @param element element to asset if disabled
     */
    public static void assertIsDisabled(MainDto mainDto, WebElement element, String errorMessage) {
        LOGGER.debug("asserting element = {} if disabled", element );
        assertFalse(ElementUtils.isElementEnabled(mainDto, element), errorMessage);
    }

    /**
     * @param mainDto main dto
     * @param by locator of the element to assert if disabled
     */
    public static void assertIsDisabled(MainDto mainDto, By by, String errorMessage) {
        LOGGER.debug("asserting element by = {} if disabled", by );
        WebElement element = ElementUtils.findByElement(mainDto,by);
        assertFalse(ElementUtils.isElementEnabled(mainDto, element), errorMessage);
    }

    public static void assertNotClickable(MainDto mainDto, WebElement element) {
        LOGGER.debug("asserting element = {} if not clickable", element);
        boolean clickable;
        try {
            WaitUtils.waitForElementClickable(mainDto, element);
            clickable = true;
        } catch (TimeoutException | ElementClickInterceptedException e) {
            LOGGER.debug("Element is not clickable!");
            clickable = false;
        }
        AssertionUtils.assertFalse(clickable,"element is clickable, which is unexpected!");
    }

    public static void assertNotClickable(MainDto mainDto, By by, String errorMessage) {
        String isClickable = ElementUtils.getElementAttribute(mainDto, by, "clickable");
        assertEquals(isClickable, "false", errorMessage);
    }

    public static void assertChecked(MainDto mainDto, By by, String errorMessage) {
        String isChecked = ElementUtils.getElementAttribute(mainDto, by, "checked");
        assertEquals(isChecked, "true", errorMessage);
    }

    public static void assertElementNotBlank(WebElement element) {
        boolean isBlank = ElementUtils.getText(element).isBlank();
        LOGGER.debug("element = {} is blank = {}", element, isBlank);
        Assert.assertFalse(isBlank);
    }

    public static void assertAmountIsPositive(Float amount, String errorMessage) {
        LOGGER.debug("asserting if amount: {} is a positive number", amount);
        assertTrue(amount>=0, errorMessage);
    }

    public static void assertAmountIsPositive(Float amount) {
        LOGGER.debug("asserting if amount: {} is a positive number", amount);
        String errorMessage = "amount = "+ amount + " is negative! should be positive!";
        assertTrue(amount>=0, errorMessage);
    }

    public static void assertAmountIsNegative(Float amount) {
        LOGGER.debug("asserting if amount: {} is a negative number", amount);
        String errorMessage = "amount = "+ amount + " is positive! should be negative!";
        assertTrue(amount<0, errorMessage);
    }

    /**
     * @param date string to assert if is valid date syntax. e.g. "13 Jun 2022" is a valid date
     */
    public static void assertDateIsValid(String date, String errorMessage){
        boolean validDate;
        LOGGER.debug("asserting if string: {} is a valid date", date);
        String monthEng = date.split(" ")[1];
        date = date.replace(monthEng,DateUtils.convertEnglishMonthToNumber(monthEng));
        LOGGER.debug("Date part converted in numbers:{}",date);
        DateFormat df = new SimpleDateFormat("dd MM yyyy");
        try{
            df.parse(date);
            validDate = true;
            LOGGER.debug("Date is valid");
        }
        catch (ParseException e){
            validDate = false;
            LOGGER.debug("Date is invalid, assert failed");
        }
        assertTrue(validDate, errorMessage);
    }
    public static void assertDateIsValid(String date){
        String errorMessage = "date = " + date + " is not valid!";
        assertDateIsValid(date, errorMessage);
    }

    /**
     * @param time string to assert if is valid time syntax. e.g. "15:07" is a valid time
     */
    public static void assertTimeIsValid(String time, String errorMessage){
        boolean validTime = Pattern.matches("([2][0-3]|[0-1][0-9]):[0-5][0-9]",time);
        LOGGER.debug("asserting if string {} is valid time: {}",time,validTime);
        assertTrue(validTime, errorMessage);
    }

    public static void assertTimeIsValid(String time){
        String errorMessage = "time = " + time + " is invalid!";
        assertTimeIsValid(time, errorMessage);
    }

    public static void assertFastDisappearingElement(MainDto mainDto, By element, By errorMessage) {
        boolean isDisplayed = ElementUtils.isElementDisplayed(mainDto, errorMessage, 5, 200);
        int maxRetry = 5;

        while(!isDisplayed && maxRetry > 0){
            WaitUtils.waitForElementClickable(mainDto, element, 5);
            ClickUtils.click(mainDto, element);
            isDisplayed = ElementUtils.isElementDisplayed(mainDto, errorMessage, 5, 200);
            maxRetry--;
            LOGGER.debug("isDisplayed = {}, retry number = {}", isDisplayed, maxRetry);
        }
        AssertionUtils.assertTrue(isDisplayed, "ErrorMessage = " + errorMessage + " is not displayed!");
    }
}