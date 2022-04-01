package r8.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * A class for retrieving String objects used for localization from text resource files.
 */
public class TextLoader {
    private static TextLoader INSTANCE;
    private ResourceBundle bundle;
    private Locale locale;

    private TextLoader() {}

    /**
     * Gets the TextLoader instance
     * @return Instance of TextLoader
     */
    public static TextLoader getInstance() {
        if (TextLoader.INSTANCE == null) {
            TextLoader.INSTANCE = new TextLoader();
        }
        return TextLoader.INSTANCE;
    }

    /**
     * Alustetaan locale ja bundle
     */
    private void initLoader() {
        if (locale == null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("src/main/resources/proma.properties"));
                locale = new Locale(properties.getProperty("language"), properties.getProperty("country"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bundle = ResourceBundle.getBundle("lang/TextResources", locale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the resource specified by String name that should point to a key in a properties file
     * @param name is the key to a property
     * @return String associated with the property key
     */
    public String getResource(String name) {
        if (bundle == null) {
            initLoader();
        }
        return bundle.getString(name);
    }


    /*
     * Sets the current locale object to be used for retrieving the correct resource bundle
     * @param language String for locale object constructor language parameter
     * @param country String for locale object constructor country parameter

    public void setLocale(String language, String country) {
        this.locale = new Locale(language, country);
    }
            */
    /**
     * palauttaa bundleobjektin, mikäli sellainen on olemassa
     */
    public ResourceBundle getBundle(){
        if(bundle == null){
            initLoader();
        }
        return this.bundle;
    }
}
