package main.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static final String PROPERTIES_FILE = "/config.properties";
    private static final PropertyLoader INSTANCE = new PropertyLoader();

    private final Properties properties;

    private PropertyLoader() {
        properties = new Properties();
        loadPropertiesFromFile();
    }

    public static PropertyLoader getInstance() {
        return INSTANCE;
    }

    public String getBaseUrl() {
        return System.getProperty("site.url") != null ? System.getProperty("site.url") : properties.getProperty("site.url");
    }

    public String getAPIUrl() {
        return System.getProperty("api.url") != null ? System.getProperty("api.url") : properties.getProperty("api.url");
    }

    public String getBrowserName() {
        return System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
    }

    private void loadPropertiesFromFile() {
        try {
            properties.load(PropertyLoader.class.getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get properties from " + PROPERTIES_FILE, e);
        }
    }
}