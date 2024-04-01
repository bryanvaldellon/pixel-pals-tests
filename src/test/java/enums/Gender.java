package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    FEMALE("Female"),
    MALE("Male");

    private final String name;
}
