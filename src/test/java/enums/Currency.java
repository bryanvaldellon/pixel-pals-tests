package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {

    SGD(Countries.SINGAPORE, "SGD");

    private final Countries countries;
    private final String shortName;

    public static Currency getCurrencyByCountry(Countries country){
        Currency c = null;
        for(Currency currency : Currency.values()) {
            if (currency.getCountries().equals(country)) {
                c = currency;
                break;
            }
        }
        return c;
    }

}
