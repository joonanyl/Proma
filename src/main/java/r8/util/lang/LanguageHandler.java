package r8.util.lang;

import static r8.util.lang.ResourceConstants.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Class for handling app language.
 * 
 * @author Sebastian Lundin
 */
public class LanguageHandler {

    /**
     * A static method for changing current application language.
     * 
     * @param key is the key for the wanted language property.
     */
    public static void changeLanguage(String key) {
        Properties properties = new Properties();
        Locale newLocale;

        // Read properties and create new Locale, ResourceBundle and Properties object.
        try (FileInputStream inputStream = new FileInputStream(APP_RESOURCE_PATH)) {
            properties.load(inputStream);
            String[] languages = properties.getProperty(APP_LANGUAGES).split(":");
            String[] locales = properties.getProperty(APP_LOCALES).split(":");

            int newLocaleIndex = 0;
            for (int i = 0; i < languages.length; i++) {
                if (languages[i].equals(key)) {
                    newLocaleIndex = i;
                }
            }
            String[] wantedLocale = locales[newLocaleIndex].split("_");
            newLocale = new Locale(wantedLocale[0], wantedLocale[1]);

            ResourceHandler.getInstance().setLocale(newLocale);
            ResourceHandler.getInstance().setBundle(ResourceBundle.getBundle(TEXT_RESOURCE_PATH, newLocale));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Write new Properties object to app resource file (replacing the old one).
        try (FileOutputStream outputStream = new FileOutputStream(APP_RESOURCE_PATH, false)) {
            properties.replace(APP_LANGUAGE, newLocale.getLanguage());
            properties.replace(APP_COUNTRY, newLocale.getCountry());
            properties.replace(APP_LOCALE, key);
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
