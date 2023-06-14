package it.unicam.cs.mp.progettoesame.api.utils;
@FunctionalInterface
public interface CoordinatesSpeedCalculator<T extends Number> {
    T calculateCoordinates(T value, T speed, T direction);
}
