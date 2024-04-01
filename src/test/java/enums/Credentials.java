package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Credentials {

    PIXEL_PALS("", "");


    private final String username;
    private final String password;
}
