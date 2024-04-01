package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private DateUtils(){

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    /**
     * @param pattern date time pattern. for other patters you can check - https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     * @return current date time with the pattern used
     */
    public static String getCurrentDateTime(String pattern){
        return getCurrentDateTime(pattern, true);
    }

    public static String getCurrentDateTime(String pattern, boolean isLog){
        if(isLog)
            LOGGER.debug("getting current date time with pattern = {}", pattern);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime currentTime = LocalDateTime.now();
        String dateTimeAsString = dateFormat.format(currentTime);
        if(isLog)
            LOGGER.debug("current date and time is = {}", dateTimeAsString);
        return dateTimeAsString;
    }

    /**
     * @return current date time with default pattern set on out config.properties file
     */
    public static String getCurrentDateTime(){
        return getCurrentDateTime(SystemUtils.getConfig("date.time.pattern.default"));
    }

    /**
     * @param pattern date pattern. for other patters you can check - https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     * @param years years to add
     * @return current date time with the pattern used
     */
    public static String getCurrentDatePlusYears(String pattern, long years){
        LOGGER.debug("getting current date with pattern = {} plus years = {}", pattern, years);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime currentTime = LocalDateTime.now().plusYears(years);
        String dateAsString = dateFormat.format(currentTime);
        LOGGER.debug("result date is = {}", dateAsString);
        return dateAsString;
    }

    /**
     * @param years years to add
     * @return current date plus years with default pattern set on out config.properties file
     */
    public static String getCurrentDatePlusYears(long years){
        return getCurrentDatePlusYears(SystemUtils.getConfig("date.pattern.default"), years);
    }

    public static String getMinusYearsFromCurrentDate(String years){
        LOGGER.debug("getting date between specified years");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDateMinusYears = LocalDateTime.now().minusYears(Long.parseLong(years));
        String dateTimeAsString = dateFormat.format(currentDateMinusYears);
        LOGGER.debug("date chosen = {}", dateTimeAsString);
        return dateTimeAsString;
    }

    public static int getAgeFromDateOfBirth(int year, int month, int date){
        LOGGER.debug("getting age from the DOB");
        LocalDate birthdate = LocalDate.of(year, month, date);
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdate, now);
        int age = period.getYears();
        LOGGER.debug("age = {}", age);
        return age;
    }
    /**
     * @param addedDays days to add
     * @param format format datetime
     * @return current date plus days with yyyy-MM-dd'T'HH:mm
     */
    public static String getCurrentDateTimeZone(Long addedDays, String country, String format){
        return getCurrentDateTimeZone(addedDays, 0, country, format);
    }

    /**
     * @param addedDays added days for the timezone
     * @param addedHours added hours for the timezone
     * @param country country of timezone
     * @param format strign format of the date time
     * @return returns current datetime of the chosen timezone when added hours or days
     */
    public static String getCurrentDateTimeZone(Long addedDays, int addedHours, String country, String format){
        LOGGER.debug("getting current date of time zone = {} with added days = {} and added hours = {}", country, addedDays, addedHours);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
        ZonedDateTime currentDateAddDays = ZonedDateTime.now(ZoneId.of(country)).plusDays(addedDays).plusHours(addedHours);
        LOGGER.debug("Zone id = {}", ZoneId.of(country));
        String dateTimeAsString = dateFormat.format(currentDateAddDays);
        LOGGER.debug("date time chosen = {}", dateTimeAsString);
        return dateTimeAsString;
    }
    public static String getCurrentDateTimeZone(int addedHours, String country, String pattern){
        return getCurrentDateTimeZone((long)0, addedHours, country, pattern);
    }

    public static String getCurrentDateTimeZone(String country, String pattern){
        return getCurrentDateTimeZone(0, country, pattern);
    }
    /**
     * @param month month in english to convert. e.g. Jan
     * @return month in number. e.g. 01
     */
    public static String convertEnglishMonthToNumber(String month){
        LOGGER.debug("Convert month to number:{}", month);
        String monthLower = month.toLowerCase();
        switch (monthLower){
            case "jan":
                return "01";
            case "feb":
                return "02";
            case "mar":
                return "03";
            case "apr":
                return "04";
            case "may":
                return "05";
            case "jun":
                return "06";
            case "jul":
                return "07";
            case "aug":
                return "08";
            case "sept":
            case "sep":
                return "09";
            case "oct":
                return "10";
            case "nov":
                return "11";
            case "dec":
                return "12";
            default: ExceptionUtils.tryCatchExceptionHandler("invalid month = " + month);
        }
        LOGGER.debug("Convert failed: Cannot identify the month in english");
        return month;
    }

    /**
     * @param firstDateTime 1st date time to compare in "dd MM yyyy, HH:mm" format. e.g. 30 06 2022, 00:20
     * @param secondDateTime 2nd date time to compare in "dd MM yyyy, HH:mm" format. e.g. 29 06 2022, 11:43
     * @return compare result in string. e.g. 1st dateTime newer than 2nd
     */
    public static String compareOrderOfTwoDateTime(String firstDateTime, String secondDateTime) {
        LOGGER.debug("Compare 2 DateTime - prev: {}, next: {}", firstDateTime, secondDateTime);
        DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
        try {
            Date d1 = df.parse(firstDateTime);
            Date d2 = df.parse(secondDateTime);
            if (d1.compareTo(d2) > 0) {
                return "1st dateTime newer than 2nd";
            } else if (d1.compareTo(d2) < 0) {
                return "1st dateTime older than 2nd";
            } else {
                return "1st dateTime equals 2nd, not newer/older";
            }
        } catch (ParseException e){
            LOGGER.debug("ParseException occured!");
            return "ParseException";
        }
    }

    /**
     * @param afterDateTime 1st date time to compare in "yyyy-MM-dd'T'HH:mm:ss" format. e.g. 2022-07-12T17:51:34
     * @param beforeDateTime 2nd date time to compare in "yyyy-MM-dd'T'HH:mm:ss" format. e.g. 2022-07-12T16:51:34
     * @return diff hours between 2 datetime in long. e.g. 1L (i.e. afterDateTime is 1 hour later than beforeDateTime)
     */
    public static Long getDiffHoursOfTwoDateTime(String afterDateTime, String beforeDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        try {
            LocalDateTime dAfter = LocalDateTime.parse(afterDateTime, format);
            LocalDateTime dBefore = LocalDateTime.parse(beforeDateTime, format);
            LOGGER.debug("Compare 2 DateTime - after: {}, before: {}", dAfter, dBefore);
            return ChronoUnit.HOURS.between(dBefore, dAfter);
        } catch (DateTimeParseException e){
            LOGGER.debug("DateTimeParseException occured. Return -1 as diff.");
            return -1L;
        }
    }
    public static boolean isDateTimeInRangeFromNow(String givenDateTime, int rangeMinute) {
        return isDateTimeInRangeFromNow("local", givenDateTime, rangeMinute);
    }

    public static boolean isDateTimeInRangeFromNow(String timeZoneId, String givenDateTime, int rangeMinute) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");
        LocalDateTime dateNow = LocalDateTime.now();
        if (!timeZoneId.equals("local")) {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
            df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
            dateNow = LocalDateTime.parse(df.format(date), format);
        }
        LocalDateTime dateGiven = LocalDateTime.parse(givenDateTime, format);
        LocalDateTime dateNowAddMinutes = dateNow.plusMinutes(rangeMinute);
        LocalDateTime dateNowMinusMinutes = dateNow.minusMinutes(rangeMinute);
        int diff = dateGiven.compareTo(dateNowAddMinutes);
        long diff2 = ChronoUnit.MINUTES.between(dateGiven,dateNowMinusMinutes);
        LOGGER.debug("value of date given = {}", dateGiven);
        LOGGER.debug("value of date now = {}", dateNow);
        LOGGER.debug("value of date now minus minutes = {}", dateNowMinusMinutes);
        LOGGER.debug("value of date now add minutes = {}", dateNowAddMinutes);
        LOGGER.debug("value of diff = {}", diff);
        LOGGER.debug("value of diff2 = {}",  diff2);

        if (diff > 0) {
            LOGGER.debug("{} is greater than {}", dateGiven, dateNowAddMinutes);
            return false;
        } else if (diff2 > 0) {
            LOGGER.debug("{} is less than {}", dateGiven, dateNowMinusMinutes);
            return false;}
        else {
            LOGGER.debug("{} is not greater than {} nor less than {}", dateGiven, dateNowAddMinutes, dateNowMinusMinutes);
            return true;
        }
    }

    public static String convertDateFormat(String dateInput, String patternBefore, String patternAfter) {
        try{
            Date date1=new SimpleDateFormat(patternBefore).parse(dateInput);
            SimpleDateFormat sdf = new SimpleDateFormat(patternAfter);
            String newDateString = sdf.format(date1);
            LOGGER.debug("Generating Date Conversion: {}", newDateString);
            return newDateString;
        } catch (Exception f){
            return "Format Error";
        }
    }

    public static Boolean isDateTimeFormat(String dateTimeInput, String pattern) {
        try{
            new SimpleDateFormat(pattern).parse(dateTimeInput);
            LOGGER.debug("date = {} is a fit format for pattern = {}", dateTimeInput, pattern);
            return true;
        } catch (Exception f){
            LOGGER.debug("date = {} does not fit format for pattern = {}", dateTimeInput, pattern);
            return false;
        }
    }

    public static boolean isDateNotMoreThanToday(String dateTimeInput, String timeZoneId) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        df.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        try {
            // Parse the input date string
            Date inputDate = df.parse(dateTimeInput);

            // Get the current date and time
            Date currentDate = new Date();

            // Compare the input date with the current date
            return !inputDate.after(currentDate);

        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Handle the parsing exception appropriately
        }
    }
}
