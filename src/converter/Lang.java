package converter;
import java.util.HashMap;
import java.util.Map;

public class Lang {
    public static boolean isRu = true;
    private static final Map<String, String> ruMap = new HashMap<>();
    private static final Map<String, String> enMap = new HashMap<>();

    static {
        ruMap.put("title", "Универсальный Конвертер");
        ruMap.put("tab_len", "Длина");
        ruMap.put("tab_wgt", "Вес");
        ruMap.put("tab_cur", "Валюта");
        ruMap.put("tab_tmp", "Температура");
        ruMap.put("tab_dat", "Данные");
        ruMap.put("tab_tim", "Время");
        ruMap.put("tab_spd", "Скорость");
        ruMap.put("tab_area", "Площадь");
        ruMap.put("lbl_val", "Значение:");
        ruMap.put("lbl_from", "Из:");
        ruMap.put("lbl_to", "В:");
        ruMap.put("lbl_res", "Результат: ");
        ruMap.put("btn_calc", "Записать в лог");
        ruMap.put("btn_copy", "Копировать");
        ruMap.put("chk_log", "Включить лог");
        ruMap.put("chk_top", "Поверх окон");
        ruMap.put("chk_dark", "Темная тема");
        ruMap.put("btn_clear", "Очистить лог");
        ruMap.put("btn_open", "Открыть файл");
        ruMap.put("btn_view", "Последние 10");
        ruMap.put("btn_edit", "Изменить курс");
        ruMap.put("menu_prog", "Программа");
        ruMap.put("menu_exit", "Выход");
        ruMap.put("menu_lang", "Язык / Language");
        ruMap.put("msg_hint", "Сколько единиц этой валюты дают за 1 USD?");
        ruMap.put("msg_upd", "Курс обновлен!");

        ruMap.put("msg_log_cleared", "История очищена!");
        ruMap.put("msg_log_empty", "История пуста.");
        ruMap.put("msg_file_err", "Ошибка файла: ");
        ruMap.put("msg_no_file", "Файл истории еще не создан.");

        enMap.put("title", "Universal Converter");
        enMap.put("tab_len", "Length");
        enMap.put("tab_wgt", "Weight");
        enMap.put("tab_cur", "Currency");
        enMap.put("tab_tmp", "Temperature");
        enMap.put("tab_dat", "Data");
        enMap.put("tab_tim", "Time");
        enMap.put("tab_spd", "Speed");
        enMap.put("tab_area", "Area");
        enMap.put("lbl_val", "Value:");
        enMap.put("lbl_from", "From:");
        enMap.put("lbl_to", "To:");
        enMap.put("lbl_res", "Result: ");
        enMap.put("btn_calc", "Save to Log");
        enMap.put("btn_copy", "Copy");
        enMap.put("chk_log", "Enable Log");
        enMap.put("chk_top", "Always on Top");
        enMap.put("chk_dark", "Dark Mode");
        enMap.put("btn_clear", "Clear Log");
        enMap.put("btn_open", "Open File");
        enMap.put("btn_view", "Last 10");
        enMap.put("btn_edit", "Edit Rate");
        enMap.put("menu_prog", "Program");
        enMap.put("menu_exit", "Exit");
        enMap.put("menu_lang", "Language");
        enMap.put("msg_hint", "How many units of this currency for 1 USD?");
        enMap.put("msg_upd", "Rate updated!");

        enMap.put("msg_log_cleared", "History cleared!");
        enMap.put("msg_log_empty", "History is empty.");
        enMap.put("msg_file_err", "File error: ");
        enMap.put("msg_no_file", "History file does not exist yet.");
    }

    public static String get(String key) {
        return isRu ? ruMap.getOrDefault(key, key) : enMap.getOrDefault(key, key);
    }
}