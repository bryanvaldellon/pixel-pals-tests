package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiResponseCode {

    SUCCESS(200),
    SUCCESS_CREATED(201),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    FORBIDDEN(403);

    private final int code;
}
