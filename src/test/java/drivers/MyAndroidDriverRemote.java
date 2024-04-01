package drivers;

import io.appium.java_client.android.AndroidDriver;
import lombok.NoArgsConstructor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExceptionUtils;
import utils.SystemUtils;

import java.net.MalformedURLException;
import java.net.URL;

@NoArgsConstructor
public class MyAndroidDriverRemote extends MyDriverRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAndroidDriverRemote.class);

    /**
     * launch android remote driver
     * @param userName username for the remote driver
     * @param accessKey accesskey/ password for the remote driver
     * @param capabilities capabilities to use
     */
    protected AndroidDriver launchAndroidDriverRemote(String userName, String accessKey, MutableCapabilities capabilities) {
        LOGGER.debug("launching android driver remote! capabilities = {}", capabilities);
        AndroidDriver androidDriver = null;
        URL url = null;
        try {
            String urlString = "https://"+ userName +":"+ accessKey + SystemUtils.getConfig("hub.url");
            LOGGER.debug("urlString = {}", urlString);
            url = new URL(urlString);
            androidDriver = new AndroidDriver(url, capabilities);
        }
        catch (MalformedURLException e) {
            ExceptionUtils.tryCatchExceptionHandler("MalformedURLException occured!", e);
        }
        catch (SessionNotCreatedException e){
            LOGGER.debug("SessionNotCreatedException occurred! will retry to create session!");
            androidDriver = new AndroidDriver(url, capabilities);
            LOGGER.debug("new session success!");
        }
        return androidDriver;
    }

    protected AndroidDriver launchAndroidDriverRemote(String userName, String accessKey) {
        return launchAndroidDriverRemote(userName, accessKey, getOption());
    }


}
