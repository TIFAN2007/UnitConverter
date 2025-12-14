package converter;

public class LengthConverter extends BaseConverter {
    public LengthConverter(int langIndex) {
        switch (langIndex) {
            case Lang.RU:
                conversionRates.put("Метры", 1.0);
                conversionRates.put("Километры", 1000.0);
                conversionRates.put("Сантиметры", 0.01);
                conversionRates.put("Миллиметры", 0.001);
                conversionRates.put("Мили", 1609.34);
                conversionRates.put("Ярды", 0.9144);
                conversionRates.put("Футы", 0.3048);
                break;
            case Lang.EN:
            default:
                conversionRates.put("Meters", 1.0);
                conversionRates.put("Kilometers", 1000.0);
                conversionRates.put("Centimeters", 0.01);
                conversionRates.put("Millimeters", 0.001);
                conversionRates.put("Miles", 1609.34);
                conversionRates.put("Yards", 0.9144);
                conversionRates.put("Feet", 0.3048);
                break;
        }
    }
}