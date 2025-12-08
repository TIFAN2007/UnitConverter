package converter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

interface Converter {
    double convert(double value, String fromUnit, String toUnit);
    String[] getUnitNames();
}

abstract class BaseConverter implements Converter {
    protected Map<String, Double> conversionRates = new HashMap<>();

    @Override
    public double convert(double value, String fromUnit, String toUnit) {
        if (!conversionRates.containsKey(fromUnit) || !conversionRates.containsKey(toUnit)) {
            throw new IllegalArgumentException("Unknown unit");
        }

        double fromRate = conversionRates.get(fromUnit);
        double toRate = conversionRates.get(toUnit);

        return value * (fromRate / toRate);
    }

    @Override
    public String[] getUnitNames() {
        Set<String> keys = conversionRates.keySet();
        return keys.toArray(new String[0]);
    }
}

class LengthConverter extends BaseConverter {
    public LengthConverter() {
        conversionRates.put("Метры", 1.0);
        conversionRates.put("Километры", 1000.0);
        conversionRates.put("Сантиметры", 0.01);
        conversionRates.put("Миллиметры", 0.001);
        conversionRates.put("Мили", 1609.34);
        conversionRates.put("Ярды", 0.9144);
        conversionRates.put("Футы", 0.3048);
    }
}

class WeightConverter extends BaseConverter {
    public WeightConverter() {
        conversionRates.put("Граммы", 1.0);
        conversionRates.put("Килограммы", 1000.0);
        conversionRates.put("Тонны", 1000000.0);
        conversionRates.put("Фунты", 453.592);
        conversionRates.put("Унции", 28.3495);
    }
}

class CurrencyConverter extends BaseConverter {
    public CurrencyConverter() {
        conversionRates.put("USD", 1.0);
        conversionRates.put("EUR", 1.10);
        conversionRates.put("RUB", 0.011);
        conversionRates.put("CNY", 0.14);
        conversionRates.put("GBP", 1.27);
    }
}

class TemperatureConverter implements Converter {
    @Override
    public double convert(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) return value;

        double celsius = switch (fromUnit) {
            case "Celsius" -> value;
            case "Fahrenheit" -> (value - 32) * 5.0 / 9.0;
            case "Kelvin" -> value - 273.15;
            default -> 0;
        };

        return switch (toUnit) {
            case "Celsius" -> celsius;
            case "Fahrenheit" -> celsius * 9.0 / 5.0 + 32;
            case "Kelvin" -> celsius + 273.15;
            default -> 0;
        };
    }

    @Override
    public String[] getUnitNames() {
        return new String[]{"Celsius", "Fahrenheit", "Kelvin"};
    }
}

class DataConverter extends BaseConverter {
    public DataConverter() {
        conversionRates.put("Bit", 1.0);
        conversionRates.put("Byte", 8.0);
        conversionRates.put("Kilobyte", 8192.0);
        conversionRates.put("Megabyte", 8388608.0);
        conversionRates.put("Gigabyte", 8589934592.0);
        conversionRates.put("Terabyte", 8796093022208.0);
    }
}