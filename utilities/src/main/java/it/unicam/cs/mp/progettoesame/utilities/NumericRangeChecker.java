package it.unicam.cs.mp.progettoesame.utilities;

/**
 * Interfaccia funzionale per controllare se
 * un valore è compreso in un range specificato
 * @param <T> il tipo dei valori da specificare, che deve
 *           essere per forza numerico.
 */
@FunctionalInterface
public interface NumericRangeChecker<T extends Number & Comparable<T>> {
    /**
     * Metodo che controlla se il valore <code>value</code> è compreso tra
     * <code>minValue</code> e <code>maxValue</code>
     * @param value il valore da controllare
     * @param maxValue il valore massimo che può assumere
     * @param minValue il valore minimo che può assumero
     * @return true se <code>value</code> è nel range, false altrimenti
     */
    boolean isBetween(T value, T maxValue, T minValue);

    /**
     * Funzione di default che utilizza double come parametro di tipo
     */
    NumericRangeChecker<Double> DEFAULT_CHECKER = (value, maxValue, minValue) ->
            value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0;
}
