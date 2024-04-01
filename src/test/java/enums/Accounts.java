package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.ExceptionUtils;

@AllArgsConstructor
@Getter
public enum Accounts {

    PIXEL_PALS(Apps.PIXEL_PALS,null, null,null, Credentials.PIXEL_PALS);


    private final Apps app;
    private final Credentials dev;
    private final Credentials staging;
    private final Credentials sandbox;
    private final Credentials prod;

    public Credentials getCredentials(String testEnv){
        switch (testEnv){
            case "dev": return this.getDev();
            case "staging":
            case "asb":
                return this.getStaging();
            case "sandbox": return this.getSandbox();
            case "prod": return this.getProd();
            default:
                ExceptionUtils.tryCatchExceptionHandler("environment = " + testEnv + " is not valid!");
        }
        return getStaging();
    }

}