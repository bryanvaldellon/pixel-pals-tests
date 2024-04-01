package drivers;


import org.openqa.selenium.MutableCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

public class MyDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDriver.class);

    protected MutableCapabilities option;

    public MutableCapabilities getOption() {
        LOGGER.debug("getting driver option = {}", option);
        return option;
    }

    public MyDriver() {
    }


    public void setOption(MutableCapabilities option) {
        LOGGER.debug("setting driver option = {}", option);
        this.option = option;
    }

    protected void setCapability(String name, Object value){
        LOGGER.debug("setting capabilities name = {} value = {}", name, value);
        option.setCapability(name, value);
    }

    protected void setCapabilityUserName(){
        setCapability("user", SystemUtils.getSystemProperty("browserstack.user"));
    }

    protected void setCapabilityAccessKey(){
        setCapability("accessKey",SystemUtils.getSystemProperty("browserstack.key"));
    }

}
