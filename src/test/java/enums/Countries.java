package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Countries {

    SINGAPORE("SG", "Singapore", "Singaporean", 7, new String[]{"9", "8"}, Language.ENGLISH), //7 because the starting number is the first
    //ID will either have country code +62 or 0 as the first number + 8 as the starting number containing length between 10 and 11
    INDONESIA("ID", "Indonesia", "Indonesian", 11, new String[]{"8", "8"}, Language.BAHASA),
    UNITEDSTATES("US", "UnitedStates", "American", 11, new String[]{"4", "4"}, Language.ENGLISH),
    GENERIC(null, null, null, 0, null, null),
    AFGHANISTAN("AF", "Afghanistan", "Afghan", 0,  null, null),
    ALBANIA("", "Albania", "Albanian", 0, null, null),
    ALGERIA("", "Algeria", "Algerian", 0, null, null),
    AMERICAN_SAMOA("", "American Samoa", "American", 0, null, null),
    ANDORRA("", "Andorra", "Andorrian", 0, null, null),
    ANGOLA("", "Angola", "Angolian", 0,null, null),
    ANGUILLA("", "Anguilla", "Anguillan", 0, null, null),
    ANTARTICA("", "Antarctica", "Antarctican", 0, null, null),
    ANTIGUA_AND_BARBUDA("", "Antigua and Barbuda", "Antiguan and Barbudan", 0, null, Language.ENGLISH),
    ARGENTINA("","Argentina", "Argentinan", 0, null, null),
    ARMENIA("", "Armenia", "Armenian", 0, null, null),
    ARUBA("", "Aruba", "Aruban", 0, null, null),
    AUSTRALIA("", "Australia", "Australian", 0, null, Language.ENGLISH),
    AUSTRIA("", "Austria", "Austrian", 0, null, null),
    AZERBAIJAN("", "Azerbaijan", "Azerbaijanian", 0, null, null),
    BAHAMAS("", "Bahamas (the)", "Bahamasian", 0, null, Language.ENGLISH),
    BAHRAIN("", "Bahrain", "Bahrainian", 0, null, null),
    BANGLADESH("", "Bangladesh", "bangldeshian", 0, null, null),
    BARBADOS("", "Barbados", "Barbadonian" ,0, null, Language.ENGLISH),
    BELARUS("", "Belarus", "belarusian", 0, null, null),
    BELGIUM("", "Belgium", "belgian", 0, null, null),
    BELIZE("", "Belize", "belizan", 0, null, Language.ENGLISH),
    BENIN("", "Benin", "Benin", 0, null, null),
    BERMUDA("", "Bermuda", "bermudian", 0, null, null),
    BHUTAN("","Bhutan","Bhutanian", 0, null, null),
    BOLIVIA("", "Bolivia", "bolivian", 0, null, null),
    BONAIRE("", "Bonaire", "Bonairian", 0, null, null),
    BOSNIA_AND_HERZEGOVINA("", "Bosnia and Herzegovina", "Bosnia and Herzegovinaian",0, null, null),
    BOTSWANA("", "Botswana", "Botswanaian",0, null, Language.ENGLISH),
    BOUVET_ISLAND("", "Bouvet Island", "Bouvet Island", 0, null, null),
    BRAZIL("","Brazil", "Brazilian", 0, null, null),
    BRITISH_INDIAN_OCEAN("", "British Indian Ocean Territory", "British", 0, null, null),
    BRUNEI_DARUSSALAM("","Brunei Darussalam", "Brunei", 0, null, null),
    BULGARIA("", "Bulgaria", "Bulgarian", 0, null, null),
    BURKINA_FASO("", "Burkina Faso", "Burkina Fasoian", 0, null, null),
    BURUNDI("", "Burundi", "Burundian", 0, null, null),
    CABO_VERDE("", "Cabo Verde", "Cabo Verdian", 0, null, null),
    CAMBODIA("", "Cambodia", "Cambodian", 0, null, null),
    CAMEROON("", "Cameroon", "Cameroonian", 0, null, null),
    CANADA("", "Canada", "Canadian", 0, null, Language.ENGLISH),
    CAYMAN_ISLANDS("", "Cayman Islands", "Cayman Island", 0, null, null),
    CENTRAL_AFRICAN_REPUBLIC("", "Central African Republic", "Central African Republican", 0, null, null),
    CHAD("", "Chad", "Chadian", 0, null, null),
    CHILE("","Chile", "Chilian", 0, null, null),
    CHINA("CN", "China", "chinese", 0, null, null),
    CHRISTMAS_ISLAND("", "Christmas Island", "Christmas Island", 0, null, null),
    COCOS_KEELING_ISLAND("", "Cocos (Keeling) Islands (the)", "Cocos", 0, null, null),
    COLOMBIA("", "Colombia", "Colombian", 0, null, null),
    COMOROS("", "Comoros", "Comorian", 0, null, null),
    CONGO("", "Congo", "Congo", 0, null, null),
    COOK_ISLANDS("", "Cook Islands", "Cook Islands", 0, null, null),
    COSTA_RICA("", "Costa Rica", "Costa Rican", 0, null, null),
    CROATIA("", "Croatia", "Croatian", 0, null, null),
    CUBA("", "Cuba", "Cuba", 0, null, null),
    CURACAO("", "Curaçao", "Curaçaon", 0, null, null),
    CYPRUS("", "Cyprus", "Cyprus", 0, null, null),
    CZECHIA("", "Czechia", "Czechian", 0, null, null),
    COTE_DIVOIRE("", "Côte d'Ivoire", "Côte d'Ivoiren", 0, null, null),
    MALAYSIA("MY", "Malaysia", "Malaysian", 0, null, null);

    private final String shortName;
    private final String longName;
    private final String nationality;
    private final Integer noOfCharactersInPhone;
    private final String[] startingNumber;
    private final Language defaultLanguage;

    public static Countries getCountryByShortName(String shortName){
        Countries c = null;
        for(Countries countries : Countries.values()) {
            if (countries.getShortName().equalsIgnoreCase(shortName)) {
                c = countries;
                break;
            }
        }
        return c;
    }
}
