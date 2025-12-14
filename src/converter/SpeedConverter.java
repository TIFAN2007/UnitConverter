package converter;

public class SpeedConverter extends BaseConverter {
    public SpeedConverter(int langIndex) {
        switch (langIndex) {
            case Lang.RU:
                conversionRates.put("м/с", 1.0);
                conversionRates.put("км/ч", 0.277778);
                conversionRates.put("мили/ч", 0.44704);
                conversionRates.put("узлы", 0.514444);
                break;
            case Lang.EN:
            default:
                conversionRates.put("m/s", 1.0);
                conversionRates.put("km/h", 0.277778);
                conversionRates.put("mph", 0.44704);
                conversionRates.put("knots", 0.514444);
                break;
        }
    }
}