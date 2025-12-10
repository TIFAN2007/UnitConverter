package converter;

public class DataConverter extends BaseConverter {
    public DataConverter(boolean isRu) {
        if (isRu) {
            conversionRates.put("Бит", 1.0);
            conversionRates.put("Байт", 8.0);
            conversionRates.put("Килобайт", 8192.0);
            conversionRates.put("Мегабайт", 8388608.0);
            conversionRates.put("Гигабайт", 8589934592.0);
            conversionRates.put("Терабайт", 8796093022208.0);
        } else {
            conversionRates.put("Bit", 1.0);
            conversionRates.put("Byte", 8.0);
            conversionRates.put("Kilobyte", 8192.0);
            conversionRates.put("Megabyte", 8388608.0);
            conversionRates.put("Gigabyte", 8589934592.0);
            conversionRates.put("Terabyte", 8796093022208.0);
        }
    }
}