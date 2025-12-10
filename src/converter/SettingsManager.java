package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {
    private static final String CONFIG_FILE = System.getProperty("user.home") + File.separator + "converter_settings.properties";

    public static void saveSettings(boolean isRu, boolean isDark, boolean isTop, int tabIndex) {
        Properties props = new Properties();
        props.setProperty("lang", isRu ? "ru" : "en");
        props.setProperty("dark", String.valueOf(isDark));
        props.setProperty("top", String.valueOf(isTop));
        props.setProperty("tab", String.valueOf(tabIndex));

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Converter Settings");
        } catch (IOException ignored) {
        }
    }

    public static Properties loadSettings() {
        File file = new File(CONFIG_FILE);
        Properties props = new Properties();
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
            } catch (IOException ignored) {
            }
        }
        return props;
    }
}