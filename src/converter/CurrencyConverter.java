package converter;

public class CurrencyConverter extends BaseConverter {
    public CurrencyConverter() {
        conversionRates.put("USD", 1.0);
        conversionRates.put("EUR", 1.10);
        conversionRates.put("RUB", 0.011);
        conversionRates.put("CNY", 0.14);
        conversionRates.put("GBP", 1.27);
    }
}