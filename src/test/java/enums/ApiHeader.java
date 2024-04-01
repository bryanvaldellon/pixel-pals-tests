package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiHeader {

    AUTHORIZATION("Authorization"),
    CONTENT_TYPE("Content-Type"),
    ACCEPT("Accept"),
    CURRENCY("currency");

    private final String key;
}
