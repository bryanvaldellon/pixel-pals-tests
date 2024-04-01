package enums;

import dto.MainDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.ExceptionUtils;

@AllArgsConstructor
@Getter
public enum Apps {

    PIXEL_PALS("", "", "", "https://app.pixelpals.ai/");

    private final String dev;
    private final String test;
    private final String staging;
    private final String prod;

    public String getAppUrlEnv(MainDto mainDto){
        switch (mainDto.getTestEnv()){
            case "dev": return this.getDev();
            case "staging": return this.getStaging();
            case "prod": return this.getProd();
            default:
                ExceptionUtils.tryCatchExceptionHandler("environment = " + mainDto.getTestEnv() + " is not valid!");
        }
        return getStaging();
    }
}