package converter;

import java.util.Map;

public class CurrencyConverter extends BaseConverter {
    public CurrencyConverter(Map<String, Double> savedRates) {
        conversionRates.put("BYN", 1.0);
        conversionRates.put("USD", 3.35);
        conversionRates.put("EUR", 3.55);
        conversionRates.put("RUB", 0.034);
        conversionRates.put("CNY", 0.46);
        conversionRates.put("GBP", 4.20);

        if (savedRates != null) {
            for (Map.Entry<String, Double> entry : savedRates.entrySet()) {
                if (conversionRates.containsKey(entry.getKey())) {
                    conversionRates.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}