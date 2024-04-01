package utils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SystemUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);
    private static final String CONFIG_FILEPATH = "src/main/resources/config.properties";
    private static final String COMMON_TEST_DATA_FILEPATH = "src/main/resources/commonTestData.properties";

    public static String getSystemProperty(String prop) {
        LOGGER.debug("getting system property of = {}", prop);
        String properties = System.getProperty(prop);
        if (properties == null || properties.isEmpty()) {
            LOGGER.debug("system property null! will get default value!");
            properties = getConfig(prop + ".default");
        }
        LOGGER.debug("property = {}", properties);
        return properties;
    }

    public static String getEnvironmentVariable(String key){
        LOGGER.debug("getting env variable of key = {}", key);
        String properties = System.getenv(key);
        if (properties == null || properties.isEmpty()) {
            LOGGER.debug("system property null! will get default value!");
            properties = getConfig(key + ".default");
        }
        LOGGER.debug("env variable value = {}", properties);
        return properties;
    }

    public static void setSystemProperty(String key, String value) {
        LOGGER.debug("setting system property of key = {} to value = {}", key, value);
        System.setProperty(key, value);
    }


    public static Properties configReader(String path) {
        File propertyFilePath = new File(path);
        BufferedReader reader;
        Properties properties;

        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration not found at " + propertyFilePath);
        }
        return properties;
    }

    /**
     * @param configName config name found on src/main/resources/config.properties
     * @return returns config value
     */
    public static String getConfig(String configName) {
        LOGGER.debug("getting config of = {}", configName);
        String value = configReader(CONFIG_FILEPATH).getProperty(configName);
        LOGGER.debug("value = {}", value);
        return value;
    }

    /**
     * @param testDataName testDataName name found on src/main/resources/commonTestData.properties
     * @return returns testDataName value
     */
    public static String getCommonTestData(String testDataName) {
        LOGGER.debug("getting common test data = {}", testDataName);
        String value = configReader(COMMON_TEST_DATA_FILEPATH).getProperty(testDataName);
        LOGGER.debug("value = {}", value);
        return value;
    }

    public static int getRandomInt(int start, int end) {
        LOGGER.debug("getting random integer with range from {} to {}", start, end);
        int rand = RandomUtils.nextInt(start, end);
        LOGGER.debug("rand = {}", rand);
        return rand;
    }

    public static int getRandomInt(int end) {
        LOGGER.debug("getting random integer with starting from {} to {}", 0, end);
        int rand = RandomUtils.nextInt(0, end);
        LOGGER.debug("rand = {}", rand);
        return rand;
    }

    public static float getRandomFloat(float start, float end) {
        LOGGER.debug("getting random integer with range from {} to {}", start, end);
        float rand = RandomUtils.nextFloat(start, end);
        LOGGER.debug("rand = {}", rand);
        return rand;
    }

    public static float getRandomFloat(float end) {
        LOGGER.debug("getting random integer with starting from {} to {}", 0, end);
        float rand = RandomUtils.nextFloat(0f, end);
        LOGGER.debug("rand = {}", rand);
        return rand;
    }

    public static void threadSleep(long millis){
        LOGGER.debug("sleeping thread in = {} milliseconds", millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getAbsolutePath(String path){
        LOGGER.debug("getting absolute path of = {}", path);
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        LOGGER.debug("absolute path = {}", absolutePath);
        return absolutePath;
    }

    public static void createTextFile(String pathname) {
        LOGGER.debug("creating new file with file name = {}", pathname);
        try {
            File myObj = new File(pathname);
            if (myObj.createNewFile()) {
                LOGGER.debug("File created: {}", myObj.getName());
            } else {
                LOGGER.debug("File already exists.");
            }
        } catch (IOException e) {
            LOGGER.debug("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * @param text text to append
     * @param file file path of the file to append to
     * @param separator your separator for each appended text
     */
    public static void appendTextToFile(String text, String separator, String file) {
        appendTextToFile(text, separator, file, true);
    }
    /**
     * @param text text to append
     * @param separator your separator for each appended text
     * @param file file path of the file to append to
     * @param isPrintLogger if you want to print the text on the logger
     */
    public static void appendTextToFile(String text, String separator, String file, boolean isPrintLogger) {
        if(isPrintLogger)
            LOGGER.debug("appending text = {} to file = {}", text, file);
        text = text + " "+separator+" ";
        try {
            Files.write(Paths.get(file), text.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            LOGGER.debug("appendTextToFile failed!");
            e.printStackTrace();
        }
    }

    /**
     * save a file from the url given
     * @param urlString url of the file
     * @param path path and filename to save to
     * @return path to save to
     */
    public static String saveFileFromUrl(String urlString, String path){
        LOGGER.debug("saving the file from url = {} on path = {}", urlString, path);
        try {
            InputStream in = new URL(urlString).openStream();
            Files.copy(in, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * @param object object to get private values to
     * @param path path of the target value separated with period eg path.path.path
     * @return returns a list of values
     */
    public static List<Object> getPrivateValues(Object object, String path){
        LOGGER.debug("getting the private values of the path = {}", path);
        List<String> paths = Arrays.asList(path.split("\\."));
        Field f = null;
        List<Object> result = null;
        try{
            for(String p : paths){
                LOGGER.debug("accessing = {}", p);
                f = object.getClass().getDeclaredField(p);
                f.setAccessible(true);
                object = f.get(object);
            }
            result =  (ArrayList) object;
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            LOGGER.debug("object is not a list! will create a new list and insert object!");
            result = new ArrayList<>();
            result.add(object);
        }
        return result;
    }

    /**
     * check latest file which downloaded to given directory path
     * @param dirPath path of download folder
     * @return latest file
     */
    public static File getLatestFilefromDir(String dirPath){
        LOGGER.debug("Getting latest file from directory = {}", dirPath);
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            LOGGER.debug("No file found in dir = {}!", dirPath);
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        LOGGER.debug("Latest file get = {}", lastModifiedFile.getName());
        return lastModifiedFile;
    }

    /**
     * read csv and return row of assigned index
     * the return row will be a string format, with delimiter of comma ","
     * @param filename csv filename
     * @param index index of request row
     * @return row of assigned index
     */
    public static String getIndexRowOfCsv(String filename, int index){
        try {
            LOGGER.debug("Getting content of pdf file = {}", filename);
            BufferedReader br = new BufferedReader(new FileReader(filename));
            for (int i=1; i<=index; i++) {
                br.readLine();
            }
            String targetRow = br.readLine();
            br.close();
            LOGGER.debug("Target row content of index {} = {}", index, targetRow);
            return targetRow;
        } catch (IOException e) {
            LOGGER.debug("File not found! Will return null");
            return null;
        }
    }

    public static String getProjectDir(){
        String projDir = System.getProperty("user.dir");
        LOGGER.debug("projDir = {}", projDir);
        return projDir;
    }

    public static File[] getFilesInDir(String dir) {
        LOGGER.debug("getting list of files in the directory = {}", dir);
        File folder = new File(dir);
        return folder.listFiles();
    }

    public static String getFileNameFromDir(String fileName, String dir, String excludeFileType){
        File[] files = getFilesInDir(dir);
        String f = "";
        for(File file : files){
            if(file.getName().contains(fileName) && !file.getName().contains(excludeFileType)){
                f = file.getName();
            }
        }
        LOGGER.debug("finding file name = {} on the directory = {}", fileName, dir);
        LOGGER.debug("filename = {} found!", f);
        return f;
    }

    public static String getFileNameFromDir(String fileName, String dir){
        return getFileNameFromDir(fileName,dir,"");
    }
}
