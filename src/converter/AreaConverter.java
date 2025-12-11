package converter;

public class AreaConverter extends BaseConverter {
    public AreaConverter(boolean isRu) {
        if (isRu) {
            conversionRates.put("Кв. метры", 1.0);
            conversionRates.put("Кв. километры", 1000000.0);
            conversionRates.put("Гектары", 10000.0);
            conversionRates.put("Сотки", 100.0);
            conversionRates.put("Акры", 4046.86);
        } else {
            conversionRates.put("Sq. meters", 1.0);
            conversionRates.put("Sq. kilometers", 1000000.0);
            conversionRates.put("Hectares", 10000.0);
            conversionRates.put("Ares", 100.0);
            conversionRates.put("Acres", 4046.86);
        }
    }
}