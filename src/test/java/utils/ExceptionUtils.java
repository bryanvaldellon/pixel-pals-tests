package utils;

import io.cucumber.java.PendingException;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionUtils{

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtils.class);

    public static void objectNotFound(String message, Object obj){
        LOGGER.error("Object not found: {},{}",obj,message);
        throw new NotFoundException(message);
    }

    public static void tryCatchExceptionHandler(String message, Exception e){
        throw new AssertionError(message, e);
    }

    public static void tryCatchExceptionHandler(String message){
        throw new AssertionError(message);
    }

    public static void stopScenario(String message){
        LOGGER.debug("stopping scenario using pending exception with message = {}", message);
        throw new PendingException();
    }
}