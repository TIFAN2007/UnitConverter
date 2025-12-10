package converter;

public class TemperatureConverter implements Converter {
    private final boolean isRu;

    public TemperatureConverter(boolean isRu) {
        this.isRu = isRu;
    }

    @Override
    public double convert(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) return value;
        double celsius = 0;

        if (isUnit(fromUnit, "Celsius", "Цельсий")) celsius = value;
        else if (isUnit(fromUnit, "Fahrenheit", "Фаренгейт")) celsius = (value - 32) * 5.0 / 9.0;
        else if (isUnit(fromUnit, "Kelvin", "Кельвин")) celsius = value - 273.15;

        if (isUnit(toUnit, "Celsius", "Цельсий")) return celsius;
        else if (isUnit(toUnit, "Fahrenheit", "Фаренгейт")) return celsius * 9.0 / 5.0 + 32;
        else if (isUnit(toUnit, "Kelvin", "Кельвин")) return celsius + 273.15;

        return 0;
    }

    private boolean isUnit(String current, String en, String ru) {
        return current.equals(en) || current.equals(ru);
    }

    @Override
    public String[] getUnitNames() {
        if (isRu) return new String[]{"Цельсий", "Фаренгейт", "Кельвин"};
        else return new String[]{"Celsius", "Fahrenheit", "Kelvin"};
    }
}