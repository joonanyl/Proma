package r8.util.lang;

import r8.App;

import static r8.util.lang.ResourceConstants.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Handles localized language properties and switching locale.
 * 
 * @author Sebastian Lundin
 */
public class LanguageHandler {
    // No instantiation needed. Only static methods present.
    private LanguageHandler() {};

    /**
     * Changes current application language/locale.
     * 
     * @param key is the key for the wanted language property.
     */
    public static void changeLanguage(String key) {
        Properties properties = new Properties();
        Locale newLocale;

        // Read properties and create new Locale, ResourceBundle and Properties objects.
        try{
            properties.load(App.class.getClassLoader().getResourceAsStream("proma.properties"));
            String[] languages = properties.getProperty(APP_LANGUAGES).split(":");
            String[] locales = properties.getProperty(APP_LOCALES).split(":");

            String[] wantedLocale = locales[Arrays.asList(languages).indexOf(key)].split("_");
            newLocale = new Locale(wantedLocale[0], wantedLocale[1]);

            ResourceHandler.getInstance().setBundle(ResourceBundle.getBundle(TEXT_RESOURCE_PATH, newLocale));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Write new Properties object to main app resource file (replacing the old one,
        // not appending).
            properties.replace(APP_LANGUAGE, newLocale.getLanguage());
            properties.replace(APP_COUNTRY, newLocale.getCountry());
            properties.replace(APP_LOCALE, key);

    }

    /**
     * A wrapper method for getting localized text resources with ResourceHandler
     * from a static context.
     * 
     * @param key the property key
     * @return localized string property
     */
    public static String getText(String key) {
        return ResourceHandler.getInstance().getTextResource(key);
    }
}
