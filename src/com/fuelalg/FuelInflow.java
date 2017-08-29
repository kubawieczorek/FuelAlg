package com.fuelalg;

/**
 * Created by maciej on 18.08.17.
 */
public class FuelInflow {
    public double litresPerSecond;
    public double temperature;
    public double limitVolume = Double.POSITIVE_INFINITY;

    public void verify()
    {
        if(litresPerSecond <= 0) throw new IllegalArgumentException("litresPerSecond <= 0");
        if(temperature < -273.15) throw new IllegalArgumentException("temperature < -273.15");
        if(limitVolume <= 0) throw new IllegalArgumentException("limitVolume <= 0");
    }
}
