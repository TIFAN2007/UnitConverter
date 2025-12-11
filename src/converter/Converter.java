package converter;
public interface Converter {
    double convert(double value, String fromUnit, String toUnit);
    String[] getUnitNames();
}