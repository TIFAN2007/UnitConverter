package converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseConverter implements Converter {
    protected final Map<String, Double> conversionRates = new LinkedHashMap<>();

    @Override
    public double convert(double value, String fromUnit, String toUnit) {
        if (!conversionRates.containsKey(fromUnit) || !conversionRates.containsKey(toUnit)) {
            return 0.0;
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

    public double getRate(String unitName) {
        return conversionRates.getOrDefault(unitName, 1.0);
    }

    public Map<String, Double> getAllRates() {
        return conversionRates;
    }
}