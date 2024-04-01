package utils;

import dto.MainDto;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AndroidElementUtils {

    private AndroidElementUtils(){

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidElementUtils.class);
    /**
     * find a button using name
     * @param buttonName name string of the button
     * @return by locator of the button
     */
    public static By findByButtonName(String buttonName) {
        return By.xpath("//android.widget.Button[@content-desc='" + buttonName + "']");
    }

    /**
     * finds a view element using its name
     * @param viewName view name
     * @return by locator of the view
     */
    public static By findByViewName(String viewName) {
        String viewNameUppercase = viewName.toUpperCase();
        return By.xpath("//android.view.View[(@content-desc='" + viewName + "') or (@content-desc='" + viewNameUppercase + "')]");
    }

    /**
     * finds any element using any text
     * @param text any text name to find an element
     * @return by locator of the element
     */
    public static By findByAnyName(String text) {
        String textUppercase = text.toUpperCase();
        return By.xpath("//*[contains(@content-desc, '" + text + "') or contains(@text, '" + text + "') or contains(@content-desc, '" + textUppercase + "') or contains(@text, '" + textUppercase + "')]");
    }

    /**
     * finds any element using any text, case-insensitive
     * @param text any case-insensitive text name to find an element
     * @return by locator of the element
     */
    public static By findByAnyNameIgnoreCase(String text) {
        return By.xpath("//*[contains(lower-case(@content-desc), '" + text.toLowerCase() + "') or contains(lower-case(@text), '" + text.toLowerCase() + "')]");
    }

    /**
     * finds a text field name
     * @param fieldName field name of the element
     * @return by locator of the element
     */
    public static By findByTextInputField(String fieldName) {
        return By.xpath("//*[@text='" + fieldName + "' or ends-with(@text, ', " + fieldName + "')]");
    }

    /**
     * finds an element using its resource id
     * @param resourceId resource id of the element
     * @return by locator of the resource Id
     */
    public static By findByResourceId(String resourceId) {
        return By.xpath("//*[@resource-id='" + resourceId + "']");
    }

    /**
     * scrolls down until element is found
     * @param mainDto main dto
     * @param by by locator of the element to find
     */
    public static void scrollDownUntilElementIsFound(MainDto mainDto, By by) {
        var scrollCount = 0;
        while (ElementUtils.findByElementsNoWait(mainDto, by).size() == 0 && scrollCount < Integer.parseInt(SystemUtils.getConfig("appium.max.scroll.count"))) {
            LOGGER.debug("{} not found! will scroll! scroll count = {}", by, scrollCount);
            DriverUtils.appiumScrollDownOneScreen(mainDto);
            scrollCount++;
        }
        LOGGER.debug("{} found! scroll count = {}", by, scrollCount);
        // Need to scroll down a bit more because sometimes the element is already visible programatically, but not
        // visually. There doesn't seem to be a way to detect whether an element is visible on screen using Appium.
        DriverUtils.appiumScrollDownHalfScreen(mainDto);
    }


    public static By findByContentDescEditText(String element) {
        return By.xpath("//*[@content-desc='" + element + "']/..//android.widget.EditText");
    }
}
