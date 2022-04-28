package r8.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Luokka lokalisaatioon liittyviä operaatioita varten.
 */
public class TextLoader {
    private static final String APP_RESOURCE_PATH = "src/main/resources/proma.properties";
    private static final String TEXT_RESOURCE_PATH = "lang/TextResources";
    private static final String APP_LANGUAGE_TAG = "language";
    private static final String APP_COUNTRY_TAG = "country";

    private static TextLoader INSTANCE;
    private ResourceBundle bundle;
    private Locale locale;

    private TextLoader() {
        try {
            locale = new Locale(getAppResource(APP_LANGUAGE_TAG), getAppResource(APP_COUNTRY_TAG));
            bundle = ResourceBundle.getBundle(TEXT_RESOURCE_PATH, locale);
        } catch (NullPointerException | MissingResourceException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Palauttaa TextLoader instanssin
     * @return TextLoader
     */
    public static TextLoader getInstance() {
        if (TextLoader.INSTANCE == null) {
            TextLoader.INSTANCE = new TextLoader();
        }
        return TextLoader.INSTANCE;
    }

    public String getAppResource(String key) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(APP_RESOURCE_PATH)){
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key);
        }
    }

    /**
     * Palauttaa resurssin annetulla avaimella.
     * @param name Resurssin nimi
     * @return String objekti
     */
    public String getResource(String name) {
        return bundle.getString(name);
    }

    /**
     * palauttaa bundleobjektin, mikäli sellainen on olemassa
     */
    public ResourceBundle getBundle(){
        return this.bundle;
    }
}
