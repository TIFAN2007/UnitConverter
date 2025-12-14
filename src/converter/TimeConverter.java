package converter;

public class TimeConverter extends BaseConverter {
    public TimeConverter(int langIndex) {
        switch (langIndex) {
            case Lang.RU:
                conversionRates.put("Секунды", 1.0);
                conversionRates.put("Минуты", 60.0);
                conversionRates.put("Часы", 3600.0);
                conversionRates.put("Дни", 86400.0);
                conversionRates.put("Недели", 604800.0);
                break;
            case Lang.EN:
            default:
                conversionRates.put("Seconds", 1.0);
                conversionRates.put("Minutes", 60.0);
                conversionRates.put("Hours", 3600.0);
                conversionRates.put("Days", 86400.0);
                conversionRates.put("Weeks", 604800.0);
                break;
        }
    }
}