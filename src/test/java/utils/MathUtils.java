package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MathUtils.class);

    /**
     * @param d the float to half up. e.g. 1.246
     * @param decimalPlace which decimal place to half up. e.g. if decimalPlace = 2, result = 1.25
     * @return result of rounded float as BigDecimal data type
     */
    public static BigDecimal halfUpByDecimalPlace(float d, int decimalPlace) {
        return rounding(d, decimalPlace, RoundingMode.HALF_UP);
    }

    public static BigDecimal rounding(float f, int decimalPlace, RoundingMode roundingMode){
        LOGGER.debug("Rounding float = {} with decimal place of {} using rounding mode of {}", f, decimalPlace, roundingMode);
        BigDecimal bd = new BigDecimal(Float.toString(f));
        bd = bd.setScale(decimalPlace, roundingMode);
        LOGGER.debug("BigDecimal float value after rounded: {}", bd.floatValue());
        return bd;
    }
}
