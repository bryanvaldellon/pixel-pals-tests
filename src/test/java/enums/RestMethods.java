package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMethods {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;
}
