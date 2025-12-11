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

    public static void saveHistory(String text, Component parent) {
        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            writer.write("[" + timestamp + "] " + text + "\n");
            writer.flush();
        } catch (IOException e) {
            showError(Lang.get("msg_file_err") + e.getMessage(), parent);
        }
    }

    public static void clearHistory(Component parent) {
        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, false)) {
            writer.write("");
            JOptionPane.showMessageDialog(parent, Lang.get("msg_log_cleared"), "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            showError(Lang.get("msg_file_err") + e.getMessage(), parent);
        }
    }

    public static void openLogFile(Component parent) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(parent, Lang.get("msg_no_file"), "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            showError(Lang.get("msg_file_err") + e.getMessage(), parent);
        }
    }

    public static String getLastTenLines() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return Lang.get("msg_no_file");

        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            if (lines.isEmpty()) return Lang.get("msg_log_empty");

            int start = Math.max(0, lines.size() - 10);
            StringBuilder sb = new StringBuilder();
            for (int i = start; i < lines.size(); i++) {
                sb.append(lines.get(i)).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            return Lang.get("msg_file_err") + e.getMessage();
        }
    }

    private static void showError(String msg, Component parent) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}