package converter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseConverter implements Converter {
    protected final Map<String, Double> conversionRates = new HashMap<>();

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

    public void setRate(String unitName, double newRate) {
        if (conversionRates.containsKey(unitName)) {
            conversionRates.put(unitName, newRate);
        }
    }
}