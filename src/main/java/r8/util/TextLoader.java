package r8.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Luokka lokalisaatioon liittyviä operaatioita varten.
 */
public class TextLoader {
    private static TextLoader INSTANCE;
    private ResourceBundle bundle;
    private Locale locale;

    private TextLoader() {}

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
     * Palauttaa resurssin annetulla avaimella.
     * @param name Resurssin nimi
     * @return String objekti
     */
    public String getResource(String name) {
        if (bundle == null) {
            initLoader();
        }
        return bundle.getString(name);
    }

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
