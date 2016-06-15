package com.gomigum.game.utils;

/**
 * Created by REKA on 12-Jun-16.
 */
public class RandomGenerate {
    /**
     * Devuelve un numero aleatorio del 0-1
     * @return numero aleatorio
     */
    public int generarNumeroAleatorio() {
        return (int) (Math.random() * (1 - 0 + 1) + 0);
    }
    /**
     * Devuelve un numero aleatorio entre max min
     * @return numero aleatorio
     */
    public float generarFloatAleatorio(float max, float min) {
        return (float)(Math.random() * (max - min + max) + min);
    }

}
