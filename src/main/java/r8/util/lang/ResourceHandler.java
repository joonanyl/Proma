package r8.util.lang;

import static r8.util.lang.ResourceConstants.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Class for handling resource files.
 * 
 * @author Sebastian Lundin, Sanna Kukkonen
 */
public class ResourceHandler {

    private static ResourceHandler INSTANCE;
    private ResourceBundle bundle;
    private Locale locale;

    private ResourceHandler() {
        try {
            locale = new Locale(getAppResource(APP_LANGUAGE), getAppResource(APP_COUNTRY));
            bundle = ResourceBundle.getBundle(TEXT_RESOURCE_PATH, locale);
        } catch (NullPointerException | MissingResourceException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ResourceHandler instance.
     * 
     * @return ResourceHandler
     */
    public static ResourceHandler getInstance() {
        if (ResourceHandler.INSTANCE == null) {
            ResourceHandler.INSTANCE = new ResourceHandler();
        }
        return ResourceHandler.INSTANCE;
    }

    /**
     * A getter for application properties.
     * 
     * @param key Property key
     * @return Resource associated with the key
     * @throws IOException
     */
    public String getAppResource(String key) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(APP_RESOURCE_PATH)) {
            Properties properties = new Properties();
            properties.load(inputStream); 
            return properties.getProperty(key);
        }
    }

    /**
     * Returns a localized string associated with the key.
     * 
     * @param key Resource key
     * @return String resource
     */
    public String getTextResource(String key) {
        return bundle.getString(key);
    }

    /**
     * Return the current resourcebundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    protected void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    protected void setLocale(Locale locale) {
        this.locale = locale;
    }
}
