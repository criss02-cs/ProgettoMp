package it.unicam.cs.mp.progettoesame.api.utils;

import java.util.Comparator;
import java.util.Random;

/**
 * Classe di utilità che permette di generare un numero casuale
 * tra 2 valori.
 * @param <T> Il tipo del valore che si vuole generare, che deve essere
 *           per forza numerico
 */
public class RandomGenerator<T extends Number> {
    private Random random;

    public RandomGenerator() {
        this.random = new Random();
    }

    /**
     * Genera casualmente un numero in un rage tra <code>min</code>
     * e <code>max</code>
     * @param min il valore più piccolo possibile
     * @param max il valore più grande possibile
     * @return Il numero generato casualmente
     */
    public T getRandomNumber(T min, T max) {
        validateInput(min, max);
        double minValue = min.doubleValue();
        double maxValue = max.doubleValue();
        double randomValue = generateRandomValue(minValue, maxValue);
        return convertToType(randomValue, min);
    }

    /**
     * Metodo per verificare se i tipi di <code>min</code> e <code>max</code>
     * sono gli stessi e se implementano l'interfaccia <code>Comparable</code>
     * @param min il valore più piccolo possibile
     * @param max il valore più grande possibile
     */
    private void validateInput(T min, T max) {
        if(min.getClass() != max.getClass()) {
            throw new IllegalArgumentException("I tipi di mix e max devono essere gli stessi");
        }
        if(!(min instanceof Comparable) || !(max instanceof Comparable)){
            throw new IllegalArgumentException("I tipi devono implementare l'interfaccia Comparable");
        }
    }

    /**
     * Metodo che genera il valore casuale tra <code>minValue</code> e
     * <code>maxValue</code> utilizzando la classe <code>Random</code>
     * @param minValue il valore più piccolo possibile
     * @param maxValue il valore più grande possibile
     * @return il valore generato casualmente
     */
    private double generateRandomValue(double minValue, double maxValue) {
        return random.nextDouble() * (maxValue - minValue) + minValue;
    }

    /**
     * Converte il valore casuale generato in base al tipo di
     * <code>min</code> fornito.
     * @param randomValue valore generato dal metodo <code>generateRandomValue</code>
     * @param min il valore più piccolo possibile
     * @return ritorna lo stesso valore però con il tipo originale
     */
    private T convertToType(double randomValue, T min) {
        if (min instanceof Integer) {
            return (T) Integer.valueOf((int) randomValue);
        } else if (min instanceof Long) {
            return (T) Long.valueOf((long) randomValue);
        } else if (min instanceof Double) {
            return (T) Double.valueOf(randomValue);
        } else if (min instanceof Float) {
            return (T) Float.valueOf((float) randomValue);
        } else if (min instanceof Short) {
            return (T) Short.valueOf((short) randomValue);
        } else if (min instanceof Byte) {
            return (T) Byte.valueOf((byte) randomValue);
        } else {
            throw new IllegalArgumentException("Tipo di dato non supportato");
        }
    }
}
