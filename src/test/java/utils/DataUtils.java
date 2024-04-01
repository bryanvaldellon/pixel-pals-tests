package utils;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);
    /**
     * Generates a random first name
     *
     * @return a random first name
     */
    public static String getRandomFirstName() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        LOGGER.debug("Generating Random First Name: {}", firstName);
        return firstName;
    }

    /**
     * Generates a random last name
     *
     * @return random last name
     */
    public static String getRandomLastName() {
        Faker faker = new Faker();
        String randomLastName = faker.name().lastName().replaceAll("[^A-Za-z]", "");
        LOGGER.debug("Generating Random Last Name: {}", randomLastName);
        return randomLastName;
    }

    /**
     * Generates a random full name
     *
     * @return random full name
     */
    public static String getRandomFullName() {
        Faker faker = new Faker();
        String randomFullName = faker.name().fullName();
        LOGGER.debug("Generating Random Full Name: {}", randomFullName);
        return randomFullName;
    }

    /**
     * Generates a random company name
     *
     * @return random company name
     */

    public static String getRandomCompanyName() {
        Faker faker = new Faker();
        String randomCompanyName = faker.company().name();
        LOGGER.debug("Generating Random Company Name: {}", randomCompanyName);
        return randomCompanyName;
    }

    /**
     * Generates a random company name without space
     *
     * @return random company name without space
     */

    public static String getRandomCompanyNameWithoutSpace() {
        Faker faker = new Faker();
        String randomCompanyNameWithoutSpace = faker.company().name().replaceAll(" ","").replaceAll(",", "").replaceAll("-", "");
        LOGGER.debug("Generating Random Company Name: {}", randomCompanyNameWithoutSpace);
        return randomCompanyNameWithoutSpace;
    }

    /**
     * Generates a random mobile number within a given length
     * @param mobileNumLength mobile number length
     * @return random mobile number within a given length
     */
    public static String getRandomMobileNumber(int mobileNumLength) {
        Faker faker = new Faker();
        String randomMobileNumber = faker.number().digits(mobileNumLength);
        LOGGER.debug("Generating Random Mobile Number: {}", randomMobileNumber);
        return randomMobileNumber;
    }

    public static String getRandomMobileNumber(String prefix, int mobileNumLength) {
        return prefix + getRandomMobileNumber(mobileNumLength);
    }

    /**
     * Generates a random identification number
     * @return random identification number
     */
    public static String getRandomIdentificationNumber() {
        return getRandomIdentificationNumber(8);
    }

    /**
     * Generates a random identification number with ID length
     * @param idLength id length
     * @return random identification number with ID length
     */
    public static String getRandomIdentificationNumber(int idLength) {
        Faker faker = new Faker();
        String randomIdentificationNumber = faker.number().digits(idLength);
        LOGGER.debug("Generating Random Identification Number: {}", randomIdentificationNumber);
        return randomIdentificationNumber;
    }

    public static String getRandomEmailAddress() {
        Faker faker = new Faker();
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName().replaceAll("[^A-Za-z]", "");
        String randomCompanyName = getRandomCompanyNameWithoutSpace().replace("'","");
        String randomEMailAddress = randomFirstName + "." + randomLastName + "@" + randomCompanyName + ".com";
        LOGGER.debug("Generating Random Email Address: {}", randomEMailAddress);
        return randomEMailAddress;
    }

    public static String getRandomAddress(){
        Faker faker = new Faker();
        String randomAddress = "No." + faker.address().streetAddressNumber()
                + "," + faker.address().streetAddress()
                + "," + "Zipcode="+ faker.address().zipCode();
        LOGGER.debug("Generating Random Address with Street number, Street name and Zipcode: {}", randomAddress);
        return randomAddress;
    }

    public static String getRandomCountryName(){
        Faker faker = new Faker();
        String randomCountry = faker.address().country();
        LOGGER.debug("Generating Random Country name = {}", randomCountry);
        return randomCountry;
    }


    public static String sha1Encryption(String toBeEncrypted){
        LOGGER.debug("encrypting = {} using sha1", toBeEncrypted);
        try {
            /**getInstance() method is called with algorithm SHA-1
             * */
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            /** digest() method is called
             * to calculate message digest of the input string
             * returned as array of byte
             */
            byte[] messageDigest = md.digest(toBeEncrypted.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Encoder(String filePath) {
        String base64File = "";
        File file = new File(filePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            imageInFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        } catch (FileNotFoundException e) {
            LOGGER.debug("File not found exception is thrown {}",  e);
        } catch (IOException ioe) {
            LOGGER.debug("Exception thrown while reading the file {}", ioe);
        }
        return base64File;
    }

    public static String getRandomFutureHundredDate(String pattern) {
        Faker faker = new Faker();
        Date randomDueDate = faker.date().future(100, TimeUnit.DAYS);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String newDateString = sdf.format(randomDueDate);
        LOGGER.debug("Generating Random Due Date: {}", newDateString);
        return newDateString;
    }

    public static String getRandomIdempotencyId(){
        Faker faker = new Faker();
        String randomIdempotencyId = faker.color().name()+"_"+faker.cat().name()+"_"+faker.pokemon().name();
        LOGGER.debug("random idempotency id = {}",randomIdempotencyId);
        return randomIdempotencyId;
    }
}

