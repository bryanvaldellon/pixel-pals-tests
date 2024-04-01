package pages;

import dto.MainDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverUtils;

import java.util.List;

@Getter
public class LandingPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LandingPage.class);

    private MainDto mainDto;

    private final By app = By.id("react-unity-webgl-canvas-1");

    public LandingPage(MainDto mainDto){
        this.mainDto = mainDto;
        DriverUtils.initializePage(this.mainDto, this);
    }
}