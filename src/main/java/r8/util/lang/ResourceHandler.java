package r8.util.lang;

import r8.App;

import static r8.util.lang.ResourceConstants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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
        try {
            Properties properties = new Properties();
            properties.load(App.class.getClassLoader().getResourceAsStream("proma.properties"));
            return properties.getProperty(key);
        }catch (IOException e){
            return null;
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
