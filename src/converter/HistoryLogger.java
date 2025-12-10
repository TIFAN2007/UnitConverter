package converter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryLogger {
    private static final String FILE_NAME = System.getProperty("user.home") + File.separator + "converter_history.txt";

    public static void saveHistory(String text) {
        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] " + text + "\n");
            writer.flush();
        } catch (IOException e) {
            showError("Error writing to file: " + e.getMessage());
        }
    }

    public static void clearHistory() {
        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, false)) {
            writer.write("");
            JOptionPane.showMessageDialog(null, "Log cleared!");
        } catch (IOException e) {
            showError("Error clearing file: " + e.getMessage());
        }
    }

    public static void openLogFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "History file does not exist yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            showError("Could not open file: " + e.getMessage());
        }
    }

    public static String getLastTenLines() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return "No history yet.";

        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            if (lines.isEmpty()) return "History is empty.";

            int start = Math.max(0, lines.size() - 10);
            StringBuilder sb = new StringBuilder();
            for (int i = start; i < lines.size(); i++) {
                sb.append(lines.get(i)).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            return "Error reading history: " + e.getMessage();
        }
    }

    private static void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}