package it.unicam.cs.mp.progettoesame.api.utils;

import java.util.Objects;

/**
 * Classe che rappresenta una tupla di 2 valori
 * @param <L> tipo del primo elemento
 * @param <R> tipo del secondo elemento
 */
public class Tuple<L, R> {
    private final L item1;
    private final R item2;

    public Tuple(L item1, R item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Ritorna il primo elemento
     * @return l'elemento che è stato inserito per primo
     */
    public L getItem1() {
        return item1;
    }

    /**
     * Ritorna il secondo elemento
     * @return l'elemento che è stato inserito per secondo
     */
    public R getItem2() {
        return item2;
    }

    /**
     * Ritorna la rappresentazione in stringa dei valori nel formato
     * (item1, item2)
     * @return la stringa formattata
     */
    @Override
    public String toString() {
        return "( " + this.item1 + ", " + this.item2 + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(item1, tuple.item1) && Objects.equals(item2, tuple.item2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item1, item2);
    }

    /**
     * Metodo che costruisce una tupla partendo da 2 valori
     * @param item1 il primo elemento da salvare
     * @param item2 il secondo elemento da salvare
     * @return una tupla dei valori passati
     * @param <L> il tipo del primo elemento
     * @param <R> il tipo del secondo elemento
     */
    public static <L, R> Tuple<L, R> of(L item1, R item2) {
        return new Tuple<>(item1, item2);
    }
}
