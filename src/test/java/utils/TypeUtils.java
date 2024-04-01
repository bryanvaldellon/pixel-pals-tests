package utils;

import dto.MainDto;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeUtils.class);

    /**
     * @param element weblement to send key into
     * @param text text to type
     */
    public static void sendKeys(WebElement element, String text){
        LOGGER.debug("sending keys = {} to element = {}", text, element);
        try{
            element.sendKeys(text);
        }
        catch (Exception e){
            LOGGER.debug("general error occured on sendKeys! will retry! error - ", e);
            SystemUtils.threadSleep(500);
            element.sendKeys(text);
        }
    }

    public static void clearTextAndSendKeys(WebElement element, String text){
        LOGGER.debug("sending keys after clearing the text = {} to element = {}", text, element);
        try{
            element.clear();
            element.sendKeys(text);
        }
        catch (Exception e){
            LOGGER.debug("general error occured on sendKeys! will retry! error - ", e);
            SystemUtils.threadSleep(500);
            element.sendKeys(text);
        }
    }

    /**
     * @param mainDto main dto
     * @param by weblement to send key into
     * @param text text to type
     */
    public static void sendKeys(MainDto mainDto, By by, String text){
        LOGGER.debug("sending keys by = {}", by);
        sendKeys(ElementUtils.findByElementNoWait(mainDto, by), text);
    }

    /**
     * @param element webElement to send backspace onto
     */
    public static void sendBackSpace(WebElement element){
        LOGGER.debug("sending backspace to element = {}", element);
        try{
            element.sendKeys(Keys.BACK_SPACE);
        }
        catch (Exception e){
            LOGGER.debug("general error occured on sendKeys! will retry! error - ", e);
            SystemUtils.threadSleep(500);
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    /**
     * @param element webElement to send keyboard key onto
     * @param key keyboard key to send
     */
    public static void sendKeys(WebElement element, Keys key){
        try{
            element.sendKeys(key);
        }
        catch (Exception e){
            LOGGER.debug("general error occured on sendKeys! will retry! error - ", e);
            SystemUtils.threadSleep(500);
            element.sendKeys(key);
        }
    }

    /**
     * @param key keyboard key to send
     */
    public static void sendKeys(MainDto mainDto, Keys key){
        Actions action= new Actions(mainDto.getDriver());
        try{
            action.sendKeys(Keys.chord(key)).perform();
        }
        catch (Exception e){
            LOGGER.debug("general error occured on sendKeys! will retry! error - ", e);
            SystemUtils.threadSleep(500);
            action.sendKeys(Keys.chord(key)).perform();
        }
    }

    /**
     * sending keys like human with small delays on typing
     * @param element weblement to send key into
     * @param text text to type
     */
    public static void sendKeysLikeHuman(MainDto mainDto, WebElement element, String text){
        LOGGER.debug("sending keys like human typing = {}", text);
        for(Character c : text.toCharArray()){
            int randomTime = RandomUtils.nextInt(30, 100);
            sendKeysActionWithPause(mainDto, element, c.toString(), randomTime);
        }
    }

    /**
     * sending keys like human with small delays on typing
     * @param by locator of weblement to send key into
     * @param text text to type
     */
    public static void sendKeysLikeHuman(MainDto mainDto, By by, String text){
        sendKeysLikeHuman(mainDto, ElementUtils.findByElementNoWait(mainDto, by), text);
    }

    /**
     * @param mainDto main dto
     * @param element element to send keys
     * @param text text to type
     */
    public static void sendKeysAction(MainDto mainDto, WebElement element, String text){
        LOGGER.debug("sending keys using action = {}", text);
        Actions actions = new Actions(mainDto.getDriver());
        actions.moveToElement(element).click().sendKeys(text).perform();
    }

    /**
     * @param mainDto main dto
     * @param element elemenet to send keys
     * @param text text to type
     * @param pauseTime pause time per send
     */
    public static void sendKeysActionWithPause(MainDto mainDto, WebElement element, String text, int pauseTime){
        LOGGER.debug("sending keys using action = {} with pause of = {}", text, pauseTime);
        Actions actions = new Actions(mainDto.getDriver());
        actions.moveToElement(element).click().sendKeys(text).pause(pauseTime).perform();
    }

    public static void sendKeysAction(MainDto mainDto, By by, String text){
        sendKeysAction(mainDto, ElementUtils.findByElementNoWait(mainDto, by), text);
    }

    /**
     * Method to send keys using appium keyboard
     * @param mainDto main dto
     * @param text text to send keys
     */
    public static void sendKeysOnAppiumKeyboard(MainDto mainDto, String text){
        sendKeysOnAppiumKeyboard(mainDto, text, 0);
    }

    public static void  sendKeysOnAppiumKeyboard(MainDto mainDto, String text, int pauseTimeInMillis){
        LOGGER.debug("sending keys on appium driver keyboard = {}", text);
        Actions actions = new Actions(mainDto.getDriver());
        actions.sendKeys(text).pause(pauseTimeInMillis).build().perform();
    }

    /**
     * Method to send keys using press key
     * @param mainDto main dto
     * @param text text to send keys
     */
    public static void sendKeysUsingSendKeys(MainDto mainDto, String text) {
        for (var i = 0; i < text.length(); i++) {
            String pinDigit = text.substring(i, i + 1);
            sendKeysOnAppiumKeyboard(mainDto, pinDigit, 300);
        }
    }
    public static void sendKeysUsingPressKey(MainDto mainDto, String text) {
        for (var i = 0; i < text.length(); i++) {
            String pinDigit = text.substring(i, i + 1);
            KeyEvent keyToPress = null;
            switch (pinDigit) {
                case "0":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_0);
                    break;
                case "1":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_1);
                    break;
                case "2":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_2);
                    break;
                case "3":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_3);
                    break;
                case "4":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_4);
                    break;
                case "5":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_5);
                    break;
                case "6":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_6);
                    break;
                case "7":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_7);
                    break;
                case "8":
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_8);
                    break;
                default:
                    keyToPress = new KeyEvent(AndroidKey.DIGIT_9);
            }
            ((AndroidDriver) mainDto.getDriver()).pressKey(keyToPress);
        }
    }

    /**
     * @param element element to click and send keys
     * @param text text to enter
     */
    public static void clickAndSendKeys(WebElement element, String text){
        ClickUtils.clickAndSendKeys(element,text);
    }


    /**
     * click and send keys
     * @param mainDto main dto
     * @param by by locator
     * @param text text to type
     */
    public static void clickAndSendKeys(MainDto mainDto, By by , String text){
        ClickUtils.clickAndSendKeys(mainDto,by ,text);
    }
}
