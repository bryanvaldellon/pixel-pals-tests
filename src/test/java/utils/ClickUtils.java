package utils;

import dto.MainDto;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;

@AllArgsConstructor
public class ClickUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickUtils.class);

    /**
     * @param element element to click
     */
    public static void click(WebElement element){
        LOGGER.debug("clicking element = {}", element);
        try{
            element.click();
        }
        catch (Exception e){
            LOGGER.debug("general error occured! reclicking the element! ", e);
            element.click();
        }
        LOGGER.debug("clicked element! = {}", element);
    }


    public static void click(MainDto mainDto, By by){
        LOGGER.debug("clicking by = {}", by);
        WebElement element = mainDto.getDriver().findElement(by);
        click(element);
    }

    /**
     * clicks first element that it founds displayed. this is for elements that has desktop and mobile views
     * @param elements element list to click first element
     */
    public static void click(List<WebElement> elements) {
        LOGGER.debug("clicking first element that display from elements = {} with size of = {}", elements, elements.size());
        for (WebElement element : elements) {
            if (ElementUtils.isElementDisplayedNoWait(element)) {
                LOGGER.debug("element = {} found! clicking!", element);
                click(element);
                return;
            }
            LOGGER.debug("element list does not have anything displayed on the UI!");
        }
    }

    public static void clickUsingJS(MainDto mainDto, WebElement element){
        JSExecutorUtils.click(mainDto.getDriver(), element);
    }

    public static void clickUsingJS(MainDto mainDto, By by){
        WebElement element = ElementUtils.findByElementNoWait(mainDto, by);
        JSExecutorUtils.click(mainDto.getDriver(), element);
    }

    public static void clickMouseAction(MainDto mainDto, WebElement element){
        LOGGER.debug("clicking element using mouse action = {}", element);
        Actions action = new Actions(mainDto.getDriver());
        action.moveToElement(element).click().build().perform();
    }

    public static void clickMouseAction(MainDto mainDto, By by){
        LOGGER.debug("clicking element using mouse action by = {}", by);
        Actions action = new Actions(mainDto.getDriver());
        action.moveToElement(ElementUtils.findByElementNoWait(mainDto, by)).click().build().perform();
    }

    /**
     * @param mainDto main dto
     * @param element element to scroll and click
     */
    public static void scrollToElementAndClick(MainDto mainDto, WebElement element){
        ElementUtils.scrollToElement(mainDto, element);
        click(element);
    }

    /**
     * @param element element to click and send keys
     * @param text text to enter
     */
    public static void clickAndSendKeys(WebElement element, String text){
        click(element);
        TypeUtils.sendKeys(element,text);
    }

    /**
     * click and send keys
     * @param mainDto main dto
     * @param by by locator
     * @param text text to type
     */
    public static void clickAndSendKeys(MainDto mainDto, By by, String text){
        click(mainDto, by);
        TypeUtils.sendKeys(mainDto, by,text);
    }

    /**
     * press key using android driver
     * @param mainDto main dto
     * @param keyToPress key to press
     */
    public static void androidPressKey(MainDto mainDto, KeyEvent keyToPress) {
        LOGGER.debug("pressing key = {} using android driver", keyToPress);
        LOGGER.debug("pressing key = {} using android driver", keyToPress.hashCode());
        ((AndroidDriver) mainDto.getDriver()).pressKey(keyToPress);
    }

    /**
     * tap element on appium driver
     * @param mainDto main dto
     * @param ele element to tap
     */
    public static void tap(MainDto mainDto, WebElement ele) {
        LOGGER.debug("tapping element = {}", ele);
        new TouchAction((AndroidDriver)mainDto.getDriver())
                .tap(tapOptions().withElement(element(ele)))
                .waitAction(waitOptions(Duration.ofSeconds(1)))
                .perform();
        LOGGER.debug("tap done!");
    }

    public static void tap(MainDto mainDto, By by) {
        tap(mainDto, ElementUtils.findByElementNoWait(mainDto, by));
    }


    /**
     * click right side of an element
     * @param mainDto main dto
     * @param element element to click
     * @param addX add to x on to the right
     * @param addY add to y on to the right
     */
    public static void clickElementRightSide(MainDto mainDto, WebElement element, int addX, int addY){
        LOGGER.debug("clicking element's right side = {}", element);
        int x = element.getLocation().x;
        int y = element.getLocation().y;
        LOGGER.debug("x = {} y = {}", x, y);
        x = x + element.getSize().width + addX; //adding the width to x so it will click the rightmost side
        y = y + addY; //adjust little down
        LOGGER.debug("adjusted x = {} y = {}", x, y);
        LOGGER.debug("element dimension = {}", element.getSize());
        clickByCoordinates(mainDto, x,y);
    }

    /**
     * click right side of an element
     * @param mainDto main dto
     * @param element element to click
     */
    public static void clickElementByCoordinates(MainDto mainDto, WebElement element){
        clickElementByCoordinates(mainDto, element, 0 , 0);
    }

    /**
     * @param mainDto main dto
     * @param element element to get coord
     * @param addX add to x
     * @param addY add to y
     */
    public static void clickElementByCoordinates(MainDto mainDto, WebElement element, int addX, int addY){
        LOGGER.debug("clicking element by it's coordinate = {} with addition x = {} and y = {}", element, addX, addY);
        int x = element.getLocation().x + addX;
        int y = element.getLocation().y + addY;
        LOGGER.debug("x = {} y = {}", x, y);
        LOGGER.debug("element dimension = {}", element.getSize());
        clickByCoordinates(mainDto, x,y);
    }

    /**
     * click by coordinates
     * @param mainDto main dto
     * @param x x axis
     * @param y y axis
     */
    public static void clickByCoordinates(MainDto mainDto, int x, int y){
        LOGGER.debug("clicking by coordinates x = {} , y = {}", x , y);
        if(x > DriverUtils.getWindowSize(mainDto).width) //to avoid out off bounds click
            x = DriverUtils.getWindowSize(mainDto).width;
        if(y > DriverUtils.getWindowSize(mainDto).height)
            y = DriverUtils.getWindowSize(mainDto).height;
        LOGGER.debug("clicking x = {} y = {}", x, y);
            Actions actions = new Actions(mainDto.getDriver());
            actions.moveByOffset(x, y).click().build().perform();
    }


    public static void tapByCoordinates(MainDto mainDto, WebElement element){
        LOGGER.debug("tapping element via its coordinates = {}", element);
        Point point = element.getLocation();
        new TouchAction((AndroidDriver)mainDto.getDriver())
                .tap(PointOption.point(point))
                .waitAction(waitOptions(Duration.ofSeconds(1)))
                .perform();
    }

    /**
     * click by coordinates
     * @param mainDto main dto
     * @param x x axis
     * @param y y axis
     */
    public static void tapByCoordinates(MainDto mainDto, int x, int y){
        LOGGER.debug("tapping by coordinates x = {} , y = {}", x , y);
        if(x > DriverUtils.getWindowSize(mainDto).width) //to avoid out off bounds click
            x = DriverUtils.getWindowSize(mainDto).width;
        if(y > DriverUtils.getWindowSize(mainDto).height)
            y = DriverUtils.getWindowSize(mainDto).height;
        LOGGER.debug("tapping x = {} y = {}", x, y);
        new TouchAction((AndroidDriver)mainDto.getDriver())
                .tap(point(x, y)) //adding small points to make it to center of ele
                .waitAction(waitOptions(Duration.ofSeconds(1)))
                .perform();
    }

    /**
     * @param mainDto main dto
     */
//    public static void tapCenterOfDevice(MainDto mainDto){
//        LOGGER.debug("tapping center of the device!");
//        Dimension screenSize = AndroidUtils.getCenterCoordinates(mainDto);
//        Double x = screenSize.width*0.5;
//        Double y = screenSize.height*0.5;
//        tapByCoordinates(mainDto, x.intValue(), y.intValue());
//    }

    /**
     * click right side of an element
     * @param mainDto main dto
     * @param element element to click
     * @param addX add to x on to the right
     * @param addY add to y on to the right
     */
    public static void tapElementRightSide(MainDto mainDto, WebElement element, int addX, int addY){
        LOGGER.debug("clicking element's right side = {}", element);
        int x = element.getLocation().x;
        int y = element.getLocation().y;
        if(x > DriverUtils.getWindowSize(mainDto).width) //to avoid out off bounds click
            x = DriverUtils.getWindowSize(mainDto).width;
        if(y > DriverUtils.getWindowSize(mainDto).height)
            y = DriverUtils.getWindowSize(mainDto).height;
        LOGGER.debug("x = {} y = {}", x, y);
        x = x + element.getSize().width + addX; //adding the width to x so it will click the rightmost side
        y = y + addY; //adjust little down
        LOGGER.debug("adjusted x = {} y = {}", x, y);
        LOGGER.debug("element dimension = {}", element.getSize());
        tapByCoordinates(mainDto, x,y);
    }

    public static void tapByCoordinates(MainDto mainDto, By by){
        WebElement element = ElementUtils.findByElementNoWait(mainDto, by);
        tapByCoordinates(mainDto, element);
    }

    public static void hover(MainDto mainDto, WebElement element){
        LOGGER.debug("mouse hover on element = {}", element);
        Actions action = new Actions(mainDto.getDriver());
        action.moveToElement(element).perform();
    }

    public static void tapInElementArea(MainDto mainDto, By element, int XAddCoordinate, int YAddCoordinate){
        var deviceSize = DriverUtils.getWindowSize(mainDto);
        int iconXLocation = ElementUtils.findByElement(mainDto, element).getLocation().x;
        int iconYLocation = ElementUtils.findByElement(mainDto, element).getLocation().y;
        LOGGER.debug("Element's location x = {} y = {}", iconXLocation, iconYLocation);

        if(deviceSize.getWidth() < 1080){
            XAddCoordinate -= (XAddCoordinate * 0.3);
        }else if(deviceSize.getWidth() > 1500){
            XAddCoordinate += (XAddCoordinate * 0.3);
         }

        int XCoordinate = iconXLocation + XAddCoordinate;
        int YCoordinate = iconYLocation + YAddCoordinate;
        LOGGER.debug("Tap element x = {} y = {}", XCoordinate, YCoordinate);
        tapByCoordinates(mainDto, XCoordinate, YCoordinate);
    }

    public static void tapInElementAreaByPercentage(MainDto mainDto, By element, double XPercentage, double YPercentage){
        var deviceSize = DriverUtils.getWindowSize(mainDto);

        int iconXLocation = ElementUtils.findByElement(mainDto, element).getLocation().x;
        int iconYLocation = ElementUtils.findByElement(mainDto, element).getLocation().y;
        LOGGER.debug("Element's location x = {} y = {}", iconXLocation, iconYLocation);

        int XCoordinate = (int) Math.round(deviceSize.getWidth() - (iconXLocation * XPercentage));
        int YCoordinate = (int) Math.round(iconYLocation * YPercentage);
        LOGGER.debug("Tap element x = {} y = {}", XCoordinate, YCoordinate);
        tapByCoordinates(mainDto, XCoordinate, YCoordinate);
    }
}
