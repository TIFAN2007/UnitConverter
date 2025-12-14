package converter;

public class WeightConverter extends BaseConverter {
    public WeightConverter(int langIndex) {
        switch (langIndex) {
            case Lang.RU:
                conversionRates.put("Граммы", 1.0);
                conversionRates.put("Килограммы", 1000.0);
                conversionRates.put("Тонны", 1000000.0);
                conversionRates.put("Фунты", 453.592);
                conversionRates.put("Унции", 28.3495);
                break;
            case Lang.EN:
            default:
                conversionRates.put("Grams", 1.0);
                conversionRates.put("Kilograms", 1000.0);
                conversionRates.put("Tonnes", 1000000.0);
                conversionRates.put("Pounds", 453.592);
                conversionRates.put("Ounces", 28.3495);
                break;
        }
    }
}