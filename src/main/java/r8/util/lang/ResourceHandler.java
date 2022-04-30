package r8.util.lang;

import static r8.util.lang.ResourceConstants.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Singleton object for handling resource files.
 * 
 * @author Sebastian Lundin, Sanna Kukkonen
 */
public class ResourceHandler {

    private static ResourceHandler INSTANCE;
    private ResourceBundle bundle;

    private ResourceHandler() {
        try {
            Locale newLocale = new Locale(getAppResource(APP_LANGUAGE), getAppResource(APP_COUNTRY));
            bundle = ResourceBundle.getBundle(TEXT_RESOURCE_PATH, newLocale);
        } catch (NullPointerException | MissingResourceException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ResourceHandler instance getter.
     * 
     * @return ResourceHandler instance
     */
    public static ResourceHandler getInstance() {
        if (ResourceHandler.INSTANCE == null) {
            ResourceHandler.INSTANCE = new ResourceHandler();
        }
        return ResourceHandler.INSTANCE;
    }

    /**
     * Retrieves applivation properties.
     * 
     * @param key property key
     * @return resource associated with the key
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
     * Returns a localized string property associated with the key.
     * 
     * @param key Resource key
     * @return String resource
     */
    protected String getTextResource(String key) {
        return bundle.getString(key);
    }

    protected void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Returns the current resourcebundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }
}
