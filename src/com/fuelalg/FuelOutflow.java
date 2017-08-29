package com.fuelalg;

/**
 * Created by maciej on 18.08.17.
 */
public class FuelOutflow {
    public double litresPerSecond;
    public double limitVolume = Double.POSITIVE_INFINITY;
    public void verify()
    {
        if(litresPerSecond <= 0) throw new IllegalArgumentException("litresPerSecond <= 0");
        if(limitVolume <= 0) throw new IllegalArgumentException("limitVolume <= 0");
    }
}
