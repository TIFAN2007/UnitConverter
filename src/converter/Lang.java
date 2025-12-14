package converter;

import java.util.HashMap;
import java.util.Map;

public class Lang {
    public static final int EN = 0;
    public static final int RU = 1;

    public static int currentLang = RU;

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
        ruMap.put("lbl_prec", "Знаков:");
        ruMap.put("menu_prog", "Программа");
        ruMap.put("menu_view", "Вид");
        ruMap.put("menu_hist", "История");
        ruMap.put("menu_curr", "Валюта");
        ruMap.put("menu_lang", "Язык / Language");
        ruMap.put("menu_exit", "Выход");
        ruMap.put("chk_log", "Записывать в лог");
        ruMap.put("chk_top", "Поверх всех окон");
        ruMap.put("chk_dark", "Темная тема");
        ruMap.put("act_view_last", "Просмотр (Последние 10)");
        ruMap.put("act_open_file", "Открыть файл истории");
        ruMap.put("act_clear", "Очистить историю");
        ruMap.put("act_edit_rate", "Изменить курс валют");
        ruMap.put("btn_calc", "Записать в лог");
        ruMap.put("btn_copy", "Копировать");
        ruMap.put("msg_hint", "Сколько BYN стоит 1 единица этой валюты?");
        ruMap.put("msg_upd", "Курс обновлен и сохранен!");
        ruMap.put("msg_log_cleared", "История очищена!");
        ruMap.put("msg_log_empty", "История пуста.");
        ruMap.put("msg_file_err", "Ошибка файла: ");
        ruMap.put("msg_no_file", "Файл истории еще не создан.");
        ruMap.put("win_hist", "История операций");
        ruMap.put("err_title", "Ошибка");
        ruMap.put("err_num", "Некорректное число!");

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
        enMap.put("lbl_prec", "Decimals:");
        enMap.put("menu_prog", "Program");
        enMap.put("menu_view", "View");
        enMap.put("menu_hist", "History");
        enMap.put("menu_curr", "Currency");
        enMap.put("menu_lang", "Language");
        enMap.put("menu_exit", "Exit");
        enMap.put("chk_log", "Enable Logging");
        enMap.put("chk_top", "Always on Top");
        enMap.put("chk_dark", "Dark Mode");
        enMap.put("act_view_last", "View Last 10");
        enMap.put("act_open_file", "Open History File");
        enMap.put("act_clear", "Clear History");
        enMap.put("act_edit_rate", "Edit Exchange Rate");
        enMap.put("btn_calc", "Save to Log");
        enMap.put("btn_copy", "Copy");
        enMap.put("msg_hint", "How many BYN is 1 unit of this currency?");
        enMap.put("msg_upd", "Rate updated and saved!");
        enMap.put("msg_log_cleared", "History cleared!");
        enMap.put("msg_log_empty", "History is empty.");
        enMap.put("msg_file_err", "File error: ");
        enMap.put("msg_no_file", "History file does not exist yet.");
        enMap.put("win_hist", "Operation History");
        enMap.put("err_title", "Error");
        enMap.put("err_num", "Invalid number!");
    }

    public static String get(String key) {
        if (currentLang == RU) {
            return ruMap.getOrDefault(key, key);
        } else {
            return enMap.getOrDefault(key, key);
        }
    }
}