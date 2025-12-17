package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SettingsManager {
    private static final String CONFIG_DIR = System.getProperty("user.home") + File.separator + ".UniversalConverter";
    private static final String CONFIG_FILE = CONFIG_DIR + File.separator + "settings.properties";

    public static void saveSettings(int lang, boolean isTop, int tabIndex, int precision, Map<String, Double> rates) {
        ensureDirExists();
        Properties props = new Properties();
        props.setProperty("lang", String.valueOf(lang));
        props.setProperty("top", String.valueOf(isTop));
        props.setProperty("tab", String.valueOf(tabIndex));
        props.setProperty("prec", String.valueOf(precision));

        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            props.setProperty("rate_" + entry.getKey(), String.valueOf(entry.getValue()));
        }

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Converter Settings");
        } catch (IOException ignored) {}
    }

    public static Properties loadSettings() {
        Properties props = new Properties();
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
            } catch (IOException ignored) {}
        }
        return props;
    }

    public static Map<String, Double> loadRates(Properties props) {
        Map<String, Double> rates = new HashMap<>();
        for (String name : props.stringPropertyNames()) {
            if (name.startsWith("rate_")) {
                try {
                    String code = name.substring(5);
                    double val = Double.parseDouble(props.getProperty(name));
                    rates.put(code, val);
                } catch (NumberFormatException ignored) {}
            }
        }
        return rates;
    }

    private static void ensureDirExists() {
        try {
            Files.createDirectories(Paths.get(CONFIG_DIR));
        } catch (IOException e) {
            System.err.println("Failed to create settings directory: " + e.getMessage());
        }
    }
}