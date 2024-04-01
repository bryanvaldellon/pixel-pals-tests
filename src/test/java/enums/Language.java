package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {

    ENGLISH("en");

    private final String languageShortName;
}