package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    OK("ok"),
    PENDING("pending"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    PAID("paid"),
    EXPIRED("expired"),
    VERIFIED("verified"),
    CONTINUE("continue"),
    SUBMITTED("submitted"),
    UNVERIFIED("unverified"),
    ESCALATED("escalated"),
    VALID("valid"),
    FAILED("failed");

    private final String statusName;
}
