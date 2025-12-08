package converter;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryLogger {
    private static final String FILE_NAME = "history.txt";

    public static void saveHistory(String text) {
        File file = new File(FILE_NAME);

        try (FileWriter writer = new FileWriter(file, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] " + text + "\n");

            writer.flush();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Не удалось записать в файл!\n" +
                            "Путь: " + file.getAbsolutePath() + "\n" +
                            "Ошибка: " + e.getMessage(),
                    "Ошибка сохранения",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void clearHistory() {
        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("");
            JOptionPane.showMessageDialog(null,
                    "Файл очищен.\nПуть: " + file.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при очистке: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}